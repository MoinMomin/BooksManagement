package com.management.bookstore.controller;

import com.management.bookstore.mapper.CustomResponse;
import com.management.bookstore.model.Orders;
import com.management.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/viewallorders")
    public ResponseEntity<Map> viewAllOrders(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "10") int size){
        return CustomResponse.ok(orderService.findAllOrders(pageNumber, size));
    }
    @PostMapping("/placeorder")
    public ResponseEntity<Map> placeOrder(@RequestBody Orders orders){
        return CustomResponse.created(orderService.placeOrder(orders));
    }
    @GetMapping("/vieworderhistory")
    public ResponseEntity<Map> viewOrderHistory(@RequestParam String customerId,@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "10") int size){
        return CustomResponse.ok(orderService.findOrderByCustomerId(customerId,pageNumber, size));
    }
}
