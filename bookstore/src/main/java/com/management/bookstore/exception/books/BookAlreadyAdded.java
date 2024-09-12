package com.management.bookstore.exception.books;

public class BookAlreadyAdded extends RuntimeException{
    public BookAlreadyAdded(String msg) {
        super(msg);
    }
}
