package com.hft.orderbook;

import com.hft.orderbook.engine.MatchingEngine;
import com.hft.orderbook.engine.OrderBook;
import com.hft.orderbook.model.Order;
import com.hft.orderbook.model.OrderSide;

public class Main {
    public static void main(String[] args) {
        System.out.println("HFT Order Booking System Live");

        MatchingEngine matchingEngine = new MatchingEngine();
        OrderBook orderBook = matchingEngine.createOrderBook(1);    // Add check if it already exists.


        // An order would be created from client, and has to be placed.
        Order buyOrder1 = new Order(OrderSide.BUY, 1, 11, 100.0, 10);
        orderBook.placeOrder(buyOrder1);    // orderBook object has to be fetched based on stockId. - Think multithreaded ways here too.

        Order buyOrder2 = new Order(OrderSide.BUY, 1, 12, 101.0, 15);
        orderBook.placeOrder(buyOrder2);

        Order sellOrder1 = new Order(OrderSide.SELL, 1, 13, 95.0, 5);
        orderBook.placeOrder(sellOrder1);

        // Modify an order
        orderBook.modifyOrder(buyOrder2.getOrderId(), 102.0, 50);


        // orderBook.printSnapshot();

    }
}