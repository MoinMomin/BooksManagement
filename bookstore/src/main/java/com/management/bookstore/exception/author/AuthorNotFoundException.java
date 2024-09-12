package com.management.bookstore.exception.author;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException() {
        super("author not found");
    }
}
