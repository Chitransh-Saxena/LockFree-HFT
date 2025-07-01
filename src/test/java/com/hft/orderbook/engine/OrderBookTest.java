package com.hft.orderbook.engine;

import com.hft.orderbook.model.Order;
import com.hft.orderbook.model.OrderSide;
import com.hft.orderbook.model.Trade;
import com.hft.orderbook.pubsub.TradePublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderBookTest {
    private OrderBook orderBook;
    private List<Trade> publishedTrades;

    static class TestTradePublisher extends TradePublisher {
        private final List<Trade> trades;
        public TestTradePublisher(List<Trade> trades) {
            this.trades = trades;
        }
        @Override
        public void publish(Trade trade) {
            trades.add(trade);
        }
    }

    @BeforeEach
    void setUp() {
        publishedTrades = new ArrayList<>();
        orderBook = new OrderBook(1, new TestTradePublisher(publishedTrades));
    }

    @Test
    void testSimpleMatch() {
        Order buy = new Order(OrderSide.BUY, 1, 100.0, 10);
        Order sell = new Order(OrderSide.SELL, 1, 99.0, 10);
        orderBook.placeOrder(buy);
        orderBook.placeOrder(sell);
        assertEquals(1, publishedTrades.size(), "One trade should be published");
        Trade trade = publishedTrades.get(0);
        assertEquals(buy.getOrderId(), trade.toString().contains("BuyOrder " + buy.getOrderId()) ? buy.getOrderId() : -1);
        assertEquals(sell.getOrderId(), trade.toString().contains("SellOrder " + sell.getOrderId()) ? sell.getOrderId() : -1);
        assertEquals(99.0, trade.toString().contains("Price: 99.00") ? 99.0 : -1.0);
        assertEquals(10, trade.toString().contains("Qty: 10") ? 10 : -1);
    }

    @Test
    void testNoMatch() {
        Order buy = new Order(OrderSide.BUY, 1, 98.0, 10);
        Order sell = new Order(OrderSide.SELL, 1, 99.0, 10);
        orderBook.placeOrder(buy);
        orderBook.placeOrder(sell);
        assertEquals(0, publishedTrades.size(), "No trade should be published");
    }

    @Test
    void testConcurrentOrderPlacement() throws InterruptedException {
        int threads = 10;
        int ordersPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        for (int t = 0; t < threads; t++) {
            executor.submit(() -> {
                for (int i = 0; i < ordersPerThread; i++) {
                    orderBook.placeOrder(new Order(OrderSide.BUY, 1, 100.0, 1));
                }
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();
        // All orders should be in the order book (since no sells, no trades)
        orderBook.printSnapshot();
    }
} 