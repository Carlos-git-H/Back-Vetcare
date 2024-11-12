package com.example.back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.Quote;
import com.example.back.service.QuoteService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    
    @Autowired
    private QuoteService quoteService;

    @GetMapping
    public List<Quote> getAll(){
        return quoteService.getQuotes();
    }

    @GetMapping("/{quoteId}")
    public Optional<Quote> getById(@PathVariable ("quoteId") Long quoteId){
        return quoteService.getQuote(quoteId);
    }
}
