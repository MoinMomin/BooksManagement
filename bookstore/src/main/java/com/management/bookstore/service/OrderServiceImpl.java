package com.management.bookstore.service;

import com.management.bookstore.model.Books;
import com.management.bookstore.model.Orders;
import com.management.bookstore.repository.BookRepository;
import com.management.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookRepository bookRepository;
    @Override
    public List<Orders> findAllOrders(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return orderRepository.findAll(pageable).toList();
    }

    @Override
    public Orders placeOrder(Orders orders) {
       Books book= bookRepository.findById(orders.getBook()).get();//book stands for bookId---ISBN
        if(orders.getQuantity()>book.getStockQuantity()){
            // here we need to add functionality to send notification to manager/admin.......
        }
        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> findOrderByCustomerId(String customerId,int pageNumber,int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return orderRepository.findOrderByCustomerId(customerId,pageable).toList();
    }
}
