package com.management.bookstore.exception.books;

public class BookNotFound extends RuntimeException {
    public BookNotFound(String msg) {
        super(msg);
    }
}
