package com.management.bookstore.service;

import com.management.bookstore.exception.books.BookAlreadyAdded;
import com.management.bookstore.exception.books.BookNotFound;
import com.management.bookstore.exception.NullIdException;
import com.management.bookstore.model.Books;
import com.management.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    BookRepository bookRepository;
    @Override
    public Books addBook(Books book) {
        Optional<Books> optionalBooks= bookRepository.findByISBN(book.getISBN());
        if(optionalBooks.isPresent()){
            throw new BookAlreadyAdded("book already added ,isbn number already available("+ book.getISBN()+")");
        }
        return bookRepository.save(book);
    }

    @Override
    public Books updateBook(Books book) {
        if(book.getISBN()==null){
            throw new NullIdException();
        }
       Optional<Books> optionalBooks= bookRepository.findById(book.getISBN());
       if(optionalBooks.isEmpty()){
           throw new BookNotFound("book not found");
       }
        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(String isbn) {
        Optional<Books> optionalBooks= bookRepository.findById(isbn);
        if(optionalBooks.isEmpty()){
            throw new BookNotFound("book not found");
        }
         bookRepository.deleteById(isbn);
        return true;
    }

    @Override
    public List<Books> viewAllBooks(int pageNumber,int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return bookRepository.findAll(pageable).toList();
    }

    @Override
    public Books updateBookStock(String isbn, int quantity) {
        if(isbn==null){
            throw new NullIdException();
        }
        Optional<Books> optionalBooks= bookRepository.findById(isbn);
        if(optionalBooks.isEmpty()){
            throw new BookNotFound("book not found");
        }
        Books book=optionalBooks.get();
        book.setStockQuantity(quantity);
        return bookRepository.save(book);
    }

    @Override
    public List<Books> browseBooks(String saerchContent, int pageNumber,int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return bookRepository.browseBooks(saerchContent,pageable).toList();
    }

}
