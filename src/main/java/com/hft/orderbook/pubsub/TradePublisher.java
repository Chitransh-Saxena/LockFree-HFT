package com.hft.orderbook.pubsub;

import com.hft.orderbook.model.Trade;


/**
 * Publishes a matched trade.
 */
public class TradePublisher {

    public void publish(Trade trade) {
        // In v0.1 — just log
        System.out.println("[TRADE PUBLISH] " + trade);
    }
}
