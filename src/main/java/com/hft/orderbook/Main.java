package com.hft.orderbook;

import com.hft.orderbook.engine.MatchingEngine;
import com.hft.orderbook.engine.OrderBook;
import com.hft.orderbook.model.Order;
import com.hft.orderbook.model.OrderSide;

public class Main {
    public static void main(String[] args) {
        System.out.println("HFT Order Booking System Live");

        MatchingEngine matchingEngine = new MatchingEngine();
        // Create OrderBook for stock 1
        OrderBook orderBook = matchingEngine.createOrderBook(1);

        // Place orders
        Order buyOrder1 = new Order(OrderSide.BUY, 1, 101.0, 100);
        orderBook.placeOrder(buyOrder1);

        Order buyOrder2 = new Order(OrderSide.BUY, 1, 100.5, 50);
        orderBook.placeOrder(buyOrder2);

        Order sellOrder1 = new Order(OrderSide.SELL, 1, 100.0, 80);
        orderBook.placeOrder(sellOrder1);

        // // Modify an order
        // orderBook.modifyOrder(buyOrder2.getOrderId(), 102.0, 50);

        // // Cancel an order
        // orderBook.cancelOrder(buyOrder1.getOrderId());

        // Print snapshot
        orderBook.printSnapshot();


    }
}