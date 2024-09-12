package com.management.bookstore.controller;

import com.management.bookstore.mapper.CustomResponse;
import com.management.bookstore.mapper.jwt.JwtRequestMapper;
import com.management.bookstore.model.Users;
import com.management.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<Map> signUp(@RequestBody Users user){
       return CustomResponse.created(userService.signUp(user));
    }
    @PostMapping(value = "/signin")
    public ResponseEntity<?> signIn(@RequestBody JwtRequestMapper requestMapper)
            throws Exception {
        return ResponseEntity.ok(userService.login(requestMapper));
    }
}
