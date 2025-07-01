package com.hft.orderbook.model;

public class Trade {

    private final long buyOrderId;
    private final long sellOrderId;
    private final int stockId;
    private final double price;
    private final long quantity;

    public Trade(long buyOrderId, long sellOrderId, int stockId, double price, long quantity) {
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.stockId = stockId;
        this.price = price;
        this.quantity = quantity;
    }

    private Trade(Builder builder) {
        this.buyOrderId = builder.buyOrderId;
        this.sellOrderId = builder.sellOrderId;
        this.stockId = builder.stockId;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    @Override
    public String toString() {
        return String.format("Trade[Stock %d] BuyOrder %d ↔ SellOrder %d | Price: %.2f | Qty: %d",
                stockId, buyOrderId, sellOrderId, price, quantity);
    }

    public static class Builder {
        private long buyOrderId;
        private long sellOrderId;
        private int stockId;
        private double price;
        private long quantity;

        public Builder buyOrderId(long buyOrderId) {
            this.buyOrderId = buyOrderId;
            return this;
        }
        public Builder sellOrderId(long sellOrderId) {
            this.sellOrderId = sellOrderId;
            return this;
        }
        public Builder stockId(int stockId) {
            this.stockId = stockId;
            return this;
        }
        public Builder price(double price) {
            this.price = price;
            return this;
        }
        public Builder quantity(long quantity) {
            this.quantity = quantity;
            return this;
        }
        public Trade build() {
            return new Trade(this);
        }
    }
}
