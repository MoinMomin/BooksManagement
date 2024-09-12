package com.management.bookstore.service;

import com.management.bookstore.exception.NullIdException;
import com.management.bookstore.exception.author.AuthorNotFoundException;
import com.management.bookstore.model.Authors;
import com.management.bookstore.repository.AuthorRepository;
import com.management.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    @Override
    public Authors addAuthor(Authors authors) {

        return authorRepository.save(authors);
    }

    @Override
    public Authors updateAuthor(Authors authors) {
        if(authors.getAuthorId()==null){
            throw new NullIdException();
        }
       Optional<Authors> optionalAuthors= authorRepository.findById(authors.getAuthorId());
        if(optionalAuthors.isEmpty()){
            throw new AuthorNotFoundException();
        }
        return authorRepository.save(authors);
    }
}
