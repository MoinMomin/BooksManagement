package com.management.bookstore.service;

import com.management.bookstore.model.Authors;

public interface AuthorService {
    public Authors addAuthor(Authors authors);
    public Authors updateAuthor(Authors authors);
}
