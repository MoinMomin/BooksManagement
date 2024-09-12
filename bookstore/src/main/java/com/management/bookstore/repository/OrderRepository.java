package com.management.bookstore.repository;

import com.management.bookstore.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    public Page<Orders> findAll(Pageable pageable);
    public Page<Orders> findOrderByCustomerId(String customerId,Pageable pageable);
}
