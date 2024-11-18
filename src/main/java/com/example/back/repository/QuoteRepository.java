package com.example.back.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Quote;

import jakarta.transaction.Transactional;


@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long>{
    
    @Query("SELECT q FROM Quote q WHERE " +
           "(:date IS NULL OR q.date = :date) AND " +
           "(:status IS NULL OR q.status = :status) AND " +
           "(:dni IS NULL OR q.pet.client.dni LIKE %:dni%) AND " +
           "(:serviceName IS NULL OR q.service.name LIKE %:serviceName%)")
    Page<Quote> searchQuotes(@Param("date") LocalDate date,
                             @Param("status") Character status,
                             @Param("dni") String dni,
                             @Param("serviceName") String serviceName,
                             Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Quote q SET q.status = '0' WHERE q.idQuote = :quoteId")
    int cancelQuote(@Param("quoteId") Long quoteId);

    @Transactional
    @Modifying
    @Query("UPDATE Quote q SET q.status = '3' WHERE q.idQuote = :quoteId")
    int confirmQuote(@Param("quoteId") Long quoteId);

    @Transactional
    @Modifying
    @Query("UPDATE Quote q SET q.statusPag = '2' WHERE q.idQuote = :quoteId")
    int confirmPayment(@Param("quoteId") Long quoteId);


    
}
