package com.management.bookstore.repository;

import com.management.bookstore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    public Users findUserByUserName(String userName);
}
