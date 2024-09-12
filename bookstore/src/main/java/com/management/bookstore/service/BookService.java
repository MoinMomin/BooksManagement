package com.management.bookstore.service;

import com.management.bookstore.model.Books;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public Books addBook(Books book);
    public Books updateBook(Books book);
    public boolean deleteBook(String isbn);
    public List<Books> viewAllBooks(int pageNumber,int size);
    public Books updateBookStock(String isbn,int quantity);
    public List<Books> browseBooks(String saerchContent, int pageNumber,int size);
}
