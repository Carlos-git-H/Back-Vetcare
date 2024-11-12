package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Quote;


@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long>{
    
}
