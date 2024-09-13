package com.management.bookstore;

import com.management.bookstore.exception.books.BookAlreadyAdded;
import com.management.bookstore.exception.books.BookNotFound;
import com.management.bookstore.exception.NullIdException;
import com.management.bookstore.model.Books;
import com.management.bookstore.repository.BookRepository;
import com.management.bookstore.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook_Success() {
        Books book = new Books("12345", "Title", "Author", 19.99, 10);
        when(bookRepository.findByISBN(book.getISBN())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);

        Books result = bookService.addBook(book);
        assertEquals(book, result);
        verify(bookRepository).save(book);
    }

    @Test
    public void testAddBook_BookAlreadyAdded() {
        Books book = new Books("12345", "Title", "Author", 19.99, 10);
        when(bookRepository.findByISBN(book.getISBN())).thenReturn(Optional.of(book));

        assertThrows(BookAlreadyAdded.class, () -> bookService.addBook(book));
    }

    @Test
    public void testUpdateBook_Success() {
        Books book = new Books("12345", "Title", "Author", 19.99, 10);
        when(bookRepository.findById(book.getISBN())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Books result = bookService.updateBook(book);
        assertEquals(book, result);
        verify(bookRepository).save(book);
    }

    @Test
    public void testUpdateBook_BookNotFound() {
        Books book = new Books("12345", "Title", "Author", 19.99, 10);
        when(bookRepository.findById(book.getISBN())).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.updateBook(book));
    }

    @Test
    public void testUpdateBook_NullId() {
        Books book = new Books(null, "Title", "Author", 19.99, 10);

        assertThrows(NullIdException.class, () -> bookService.updateBook(book));
    }

    @Test
    public void testDeleteBook_Success() {
        String isbn = "12345";
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(new Books(isbn, "Title", "Author", 19.99, 10)));

        boolean result = bookService.deleteBook(isbn);
        assertTrue(result);
        verify(bookRepository).deleteById(isbn);
    }

    @Test
    public void testDeleteBook_BookNotFound() {
        String isbn = "12345";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.deleteBook(isbn));
    }

    @Test
    public void testViewAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Books> books = List.of(new Books("12345", "Title", "Author", 19.99, 10));
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(books));

        List<Books> result = bookService.viewAllBooks(0, 10);
        assertEquals(books, result);
    }

    @Test
    public void testUpdateBookStock_Success() {
        String isbn = "12345";
        Books book = new Books(isbn, "Title", "Author", 19.99, 10);
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Books result = bookService.updateBookStock(isbn, 20);
        assertEquals(20, result.getStockQuantity());
        verify(bookRepository).save(book);
    }

    @Test
    public void testUpdateBookStock_BookNotFound() {
        String isbn = "12345";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.updateBookStock(isbn, 20));
    }

    @Test
    public void testUpdateBookStock_NullId() {
        assertThrows(NullIdException.class, () -> bookService.updateBookStock(null, 20));
    }

    @Test
    public void testBrowseBooks() {
        String searchContent = "Title";
        Pageable pageable = PageRequest.of(0, 10);
        List<Books> books = List.of(new Books("12345", "Title", "Author", 19.99, 10));
        when(bookRepository.browseBooks(searchContent, pageable)).thenReturn(new PageImpl<>(books));

        List<Books> result = bookService.browseBooks(searchContent, 0, 10);
        assertEquals(books, result);
}
}