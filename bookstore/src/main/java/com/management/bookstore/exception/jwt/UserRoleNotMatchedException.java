package com.management.bookstore.exception.jwt;

public class UserRoleNotMatchedException extends RuntimeException{
    public UserRoleNotMatchedException() {
        super("user Role not satisfied or not allow to create user as manager / admin from here");
    }
}
