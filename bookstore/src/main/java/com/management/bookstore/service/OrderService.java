package com.management.bookstore.service;

import com.management.bookstore.model.Orders;

import java.util.List;

public interface OrderService {
    public List<Orders>  findAllOrders(int pageNumber,int size);
    public Orders placeOrder(Orders orders);
    public List<Orders> findOrderByCustomerId(String customerId,int pageNumber,int size);
}
