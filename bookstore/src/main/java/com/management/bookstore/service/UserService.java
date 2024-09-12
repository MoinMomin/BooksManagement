package com.management.bookstore.service;

import com.management.bookstore.mapper.jwt.JwtRequestMapper;
import com.management.bookstore.mapper.jwt.JwtResponseMapper;
import com.management.bookstore.model.Users;

public interface UserService {
    public Users signUp(Users user);
    public JwtResponseMapper login(JwtRequestMapper jwtRequest) throws Exception;
}
