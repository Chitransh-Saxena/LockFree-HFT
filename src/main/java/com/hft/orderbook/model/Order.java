package com.hft.orderbook.model;

import java.util.concurrent.atomic.AtomicLong;

public class Order {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1); // Atomic for thread-safe unique IDs

    private OrderSide orderSide;
    private long stockId;
    private long orderId;
    private double price;
    private long quantity;
    private long timestamp;


    public OrderSide getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Order(OrderSide orderSide, long stockId, double price, long quantity) {
        this.orderSide = orderSide;
        this.stockId = stockId;
        this.orderId = ID_GENERATOR.getAndIncrement(); // This should give unique order id in single JVM but concurrent environment.
        this.price = price;
        this.quantity = quantity; 
    }

    public Order(OrderSide side, int stockId, double price, long quantity) {
        this.orderId = ID_GENERATOR.getAndIncrement(); // Generate unique orderId
        this.stockId = stockId;
        this.orderSide = side;
        this.price = price;
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return String.format("Order[%d] %s %.2f x %d", orderId, orderSide, price, quantity);
    }
}
