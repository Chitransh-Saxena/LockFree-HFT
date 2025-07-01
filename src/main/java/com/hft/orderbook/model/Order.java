package com.hft.orderbook.model;

public class Order {

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

    public Order(OrderSide orderSide, long stockId, long orderId, double price, long quantity) {
        this.orderSide = orderSide;
        this.stockId = stockId;
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }
}
