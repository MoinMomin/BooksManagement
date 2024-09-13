package com.management.bookstore;

import com.management.bookstore.exception.NullIdException;
import com.management.bookstore.exception.author.AuthorNotFoundException;
import com.management.bookstore.model.Authors;
import com.management.bookstore.repository.AuthorRepository;
import com.management.bookstore.repository.BookRepository;
import com.management.bookstore.service.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Authors author;

    @BeforeEach
    void setUp() {
        author = new Authors();
        author.setAuthorId(1L);
        author.setName("John Doe");
    }

    @Test
    void testAddAuthor() {
        when(authorRepository.save(any(Authors.class))).thenReturn(author);

        Authors result = authorService.addAuthor(author);

        assertNotNull(result);
        assertEquals(author.getName(), result.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testUpdateAuthor_Success() {
        when(authorRepository.findById(author.getAuthorId())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Authors.class))).thenReturn(author);

        Authors result = authorService.updateAuthor(author);

        assertNotNull(result);
        assertEquals(author.getName(), result.getName());
        verify(authorRepository, times(1)).findById(author.getAuthorId());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testUpdateAuthor_IdNull() {
        author.setAuthorId(null);

        assertThrows(NullIdException.class, () -> authorService.updateAuthor(author));

        verify(authorRepository, times(0)).findById(anyLong());
        verify(authorRepository, times(0)).save(any(Authors.class));
    }

    @Test
    void testUpdateAuthor_AuthorNotFound() {
        when(authorRepository.findById(author.getAuthorId())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(author));

        verify(authorRepository, times(1)).findById(author.getAuthorId());
        verify(authorRepository, times(0)).save(any(Authors.class));
}
}