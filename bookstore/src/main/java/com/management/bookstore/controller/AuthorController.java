package com.management.bookstore.controller;

import com.management.bookstore.mapper.CustomResponse;
import com.management.bookstore.model.Authors;
import com.management.bookstore.model.Books;
import com.management.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @PostMapping("/addauthor")
    public ResponseEntity<Map> addAuthor(@RequestBody Authors authors){
        authorService.addAuthor(authors);
        return CustomResponse.created(authors);
    }
    @PutMapping("/updateauthor")
    public ResponseEntity<Map> updateAuthor(@RequestBody Authors authors){
        Authors  authors1= authorService.updateAuthor(authors);
        return CustomResponse.ok(authors1);
    }
}
