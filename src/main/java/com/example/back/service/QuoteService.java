package com.example.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Quote;
import com.example.back.repository.QuoteRepository;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;

    public List<Quote> getQuotes(){
        return quoteRepository.findAll();
    }

    public Optional<Quote>getQuote(Long id){
        return quoteRepository.findById(id);
    }

}
