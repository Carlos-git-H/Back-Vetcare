package com.example.back.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/create")
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
        try {
            Quote newQuote = quoteService.createQuote(quote);
            return ResponseEntity.status(HttpStatus.CREATED).body(newQuote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{quoteId}")
    public ResponseEntity<String> updateQuote(@PathVariable Long quoteId, @RequestBody Quote updatedQuote) {
        try {
            quoteService.updateQuote(quoteId, updatedQuote);
            return ResponseEntity.ok("Cita actualizada exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la cita.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchQuotes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Character status,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String serviceName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Quote> quotes = quoteService.searchQuotes(date, status, dni, serviceName, pageable);

        if (quotes.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "content", List.of(),
                "message", "No se encontraron citas con los criterios proporcionados."
            ));
        }

        return ResponseEntity.ok(Map.of(
            "content", quotes.getContent(),
            "totalPages", quotes.getTotalPages(),
            "message", "Citas encontradas con éxito."
        ));
    }

    @PutMapping("/{quoteId}/cancel")
    public ResponseEntity<?> cancelQuote(@PathVariable Long quoteId) {
        try {
            quoteService.cancelQuote(quoteId);
            return ResponseEntity.ok("Cita cancelada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{quoteId}/confirm")
    public ResponseEntity<?> confirmQuote(@PathVariable Long quoteId) {
        try {
            quoteService.confirmQuote(quoteId);
            return ResponseEntity.ok("Cita confirmada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{quoteId}/confirm-payment")
    public ResponseEntity<?> confirmPayment(@PathVariable Long quoteId) {
        try {
            quoteService.confirmPayment(quoteId);
            return ResponseEntity.ok("Pago confirmado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
