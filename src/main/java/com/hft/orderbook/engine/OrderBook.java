package com.hft.orderbook.engine;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.hft.orderbook.model.Order;
import com.hft.orderbook.model.OrderSide;
import com.hft.orderbook.model.Trade;
import com.hft.orderbook.pubsub.TradePublisher;

public class OrderBook {

    private final long stockId;
    private final TradePublisher tradePublisher;

    // BUY orders: sorted by price DESC (higher price first), then orderId ASC (time priority)
    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(
            Comparator.<Order>comparingDouble(Order::getPrice).reversed()
                    .thenComparingLong(Order::getOrderId));
    
    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(
            Comparator.<Order>comparingDouble(Order::getPrice)
                    .thenComparingLong(Order::getOrderId)
    );               

    // Map to track active orders by orderId for cancel/modify lookup
    private final Map<Long, Order> orderMap = new ConcurrentHashMap<>();

    public OrderBook(long stockId, TradePublisher tradePublisher) {
        this.stockId = stockId;
        this.tradePublisher = tradePublisher;
    }



    public long placeOrder(Order order) {

        synchronized (this) { // for v0.1 only — will remove in v0.2
            orderMap.put(order.getOrderId(), order);

            if (order.getOrderSide() == OrderSide.BUY) {
                buyOrders.add(order);
            } else {
                sellOrders.add(order);
            }

            // Try to match
            match();
        }
        return order.getOrderId();
    }

    public boolean cancelOrder(long orderId) {

        return false;
    }

    public boolean modifyOrder(long orderId, double newPrice, long newQuantity) {

        return false;
    }


    /**
     * Match BUY and SELL orders.
     */
    private void match() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            Order buy = buyOrders.peek();
            Order sell = sellOrders.peek();

            if (buy.getPrice() >= sell.getPrice()) {
                // Match found — trade at sell price
                long tradeQty = Math.min(buy.getQuantity(), sell.getQuantity());

                // Create trade
                Trade trade = new Trade.Builder()
                        .buyOrderId(buy.getOrderId())
                        .sellOrderId(sell.getOrderId())
                        .stockId((int) stockId)
                        .price(sell.getPrice())
                        .quantity(tradeQty)
                        .build();
                tradePublisher.publish(trade);

                // Update quantities
                buy.setQuantity(buy.getQuantity() - tradeQty);
                sell.setQuantity(sell.getQuantity() - tradeQty);

                // Remove orders if filled
                if (buy.getQuantity() == 0) {
                    buyOrders.poll();
                    orderMap.remove(buy.getOrderId());
                }
                if (sell.getQuantity() == 0) {
                    sellOrders.poll();
                    orderMap.remove(sell.getOrderId());
                }
            } else {
                // No match — stop
                break;
            }
        }
    }


    /**
     * Print snapshot of current order book.
     */
    public void printSnapshot() {
        synchronized (this) {
            System.out.println("== BUY ORDERS ==");
            buyOrders.stream()
                    .sorted(Comparator.<Order>comparingDouble(Order::getPrice).reversed())
                    .forEach(System.out::println);

            System.out.println("== SELL ORDERS ==");
            sellOrders.stream()
                    .sorted(Comparator.comparingDouble(Order::getPrice))
                    .forEach(System.out::println);
        }
    }
}
