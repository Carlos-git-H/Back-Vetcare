package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
