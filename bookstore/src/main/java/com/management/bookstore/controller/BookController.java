package com.management.bookstore.controller;

import com.management.bookstore.mapper.CustomResponse;
import com.management.bookstore.model.Books;
import com.management.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping("/addbooks")
    public ResponseEntity<Map> addBooks(@RequestBody Books books){
        bookService.addBook(books);
        return CustomResponse.created(books);
    }
    @PutMapping("/updatebooks")
    public ResponseEntity<Map> updateBooks(@RequestBody Books books){
       Books books1= bookService.updateBook(books);
        return CustomResponse.ok(books1);
    }
    @DeleteMapping("/deletebooks/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
       CustomResponse.ok(bookService.deleteBook(isbn));
    }
    @GetMapping("/getallbooks")
    public ResponseEntity<Map> getAllBooks( @RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "10") int size){
        List<Books> booksList = bookService.viewAllBooks(pageNumber,size);
        return CustomResponse.ok(booksList);
    }
    @PutMapping("/updatebooksstock")
    public ResponseEntity<Map> updateBooksStock(@RequestParam(required = true) String isbn,
                                                @RequestParam(required = true) int quantity){
        Books books1= bookService.updateBookStock(isbn, quantity);
        return CustomResponse.ok(books1);
    }
    @GetMapping("/browsebooks")
    public ResponseEntity<Map> browseBooks(@RequestParam String browseContent, @RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "10") int size){
        List<Books> booksList = bookService.browseBooks(browseContent,pageNumber,size);
        // for browse books, this api is public
        return CustomResponse.ok(booksList);
    }
}
