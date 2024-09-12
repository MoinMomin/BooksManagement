package com.management.bookstore.exception.order;

public class StockOutOfBoundException extends RuntimeException{
    public StockOutOfBoundException() {
        super("stock is not available as per your order --- you need more time to get your order");
    }
}
