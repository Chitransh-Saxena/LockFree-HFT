package com.hft.orderbook.engine;

import com.hft.orderbook.pubsub.TradePublisher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * MatchingEngine — manages all OrderBooks.
 */
public class MatchingEngine {

    private final TradePublisher publisher = new TradePublisher();
    private final Map<Integer, OrderBook> books = new ConcurrentHashMap<>();

    /**
     * Create new OrderBook for given stockId.
     */
    public OrderBook createOrderBook(int stockId) {
        return books.computeIfAbsent(stockId, id -> new OrderBook(id, publisher));
    }

    /**
     * Get existing OrderBook.
     */
    public OrderBook getOrderBook(int stockId) {
        return books.get(stockId);
    }
}
