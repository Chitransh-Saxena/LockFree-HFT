package com.hft.orderbook.engine;

import com.hft.orderbook.model.Order;
import com.hft.orderbook.pubsub.TradePublisher;

public class OrderBook {

    private final long stockId;
    private final TradePublisher tradePublisher;


    public OrderBook(long stockId, TradePublisher tradePublisher) {
        this.stockId = stockId;
        this.tradePublisher = tradePublisher;
    }



    public long placeOrder(Order order) {


        return 0;   // return orderId
    }

    public boolean cancelOrder(long orderId) {

        return false;
    }

    public boolean modifyOrder(long orderId, double newPrice, long newQuantity) {

        return false;
    }
}
