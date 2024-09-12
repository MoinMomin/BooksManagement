package com.management.bookstore.exception;

import com.management.bookstore.exception.books.BookAlreadyAdded;
import com.management.bookstore.exception.books.BookNotFound;
import com.management.bookstore.exception.jwt.InvalidCredentialsException;
import com.management.bookstore.exception.jwt.UserRoleNotMatchedException;
import com.management.bookstore.exception.order.StockOutOfBoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFound.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFound bnf) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", bnf.getMessage());
        return new ResponseEntity<>(body, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(BookAlreadyAdded.class)
    public ResponseEntity<Object> handleBookAlreadyAdded(BookAlreadyAdded bookAlreadyAdded) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred "+bookAlreadyAdded.getMessage());
        body.put("details", bookAlreadyAdded.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullIdException.class)
    public ResponseEntity<Object> handleNullIdException(NullIdException nullIdException) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred "+nullIdException.getMessage());
        body.put("details", nullIdException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred "+usernameNotFoundException.getMessage());
        body.put("details", usernameNotFoundException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> invalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred "+invalidCredentialsException.getMessage());
        body.put("details", invalidCredentialsException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserRoleNotMatchedException.class)
    public ResponseEntity<Object> handlerUserRoleNotMatchedException(UserRoleNotMatchedException userRoleNotMatchedException) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred "+userRoleNotMatchedException.getMessage());
        body.put("details", userRoleNotMatchedException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
