package com.management.bookstore.exception;

public class NullIdException extends RuntimeException{
    public NullIdException() {
        super("id should not null");
    }
}
