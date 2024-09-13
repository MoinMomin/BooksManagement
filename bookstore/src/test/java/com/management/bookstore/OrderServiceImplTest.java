package com.management.bookstore;

import com.management.bookstore.model.Books;
import com.management.bookstore.model.Orders;
import com.management.bookstore.repository.BookRepository;
import com.management.bookstore.repository.OrderRepository;
import com.management.bookstore.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Orders> orders = List.of(
                new Orders(1L, "12345", 2, 39.98f, "customer1", new Date())
        );
        when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(orders));

        List<Orders> result = orderService.findAllOrders(0, 10);
        assertEquals(orders, result);
    }

    @Test
    public void testPlaceOrder_Success() {
        Books book = new Books("12345", "Book Title", "Author", 10, 19); // Sufficient stock
        Orders order = new Orders(null, "12345", 2, 39.98f, "customer1", new Date());

        when(bookRepository.findById(order.getBook())).thenReturn(Optional.of(book));
        when(orderRepository.save(order)).thenReturn(order);

        Orders result = orderService.placeOrder(order);
        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    public void testPlaceOrder_InsufficientStock() {
        Books book = new Books("12345", "Book Title", "Author", 1, 1999); // Insufficient stock
        Orders order = new Orders(null, "12345", 2, 39.98f, "customer1", new Date());

        when(bookRepository.findById(order.getBook())).thenReturn(Optional.of(book));

        // Expect an exception due to insufficient stock
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(order));
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void testFindOrderByCustomerId() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Orders> orders = List.of(
                new Orders(1L, "12345", 2, 39.98f, "customer1", new Date())
        );
        when(orderRepository.findOrderByCustomerId("customer1", pageable)).thenReturn(new PageImpl<>(orders));

        List<Orders> result = orderService.findOrderByCustomerId("customer1", 0, 10);
        assertEquals(orders, result);
}
}