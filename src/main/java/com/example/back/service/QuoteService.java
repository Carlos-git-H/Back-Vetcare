package com.example.back.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Quote> searchQuotes(LocalDate date, Character status, String dni, String serviceName, Pageable pageable) {
        return quoteRepository.searchQuotes(date, status, dni, serviceName, pageable);
    }

    public void cancelQuote(Long quoteId) {
        int rowsAffected = quoteRepository.cancelQuote(quoteId);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("La cita con el ID proporcionado no existe o ya fue cancelada.");
        }
    }

    public void confirmQuote(Long quoteId) {
        int rowsAffected = quoteRepository.confirmQuote(quoteId);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("La cita con el ID proporcionado no existe o ya fue confirmada.");
        }
    }

    // Método para confirmar el pago
    public void confirmPayment(Long quoteId) {
        int rowsAffected = quoteRepository.confirmPayment(quoteId);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("La cita con el ID proporcionado no existe o ya tiene el pago confirmado.");
        }
    }

    public Quote createQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    public void updateQuote(Long quoteId, Quote updatedQuote) {
        Optional<Quote> existingQuoteOpt = quoteRepository.findById(quoteId);

        if (existingQuoteOpt.isPresent()) {
            Quote existingQuote = existingQuoteOpt.get();

            // Actualiza solo los campos necesarios
            existingQuote.setPet(updatedQuote.getPet());
            existingQuote.setService(updatedQuote.getService());
            existingQuote.setMetPag(updatedQuote.getMetPag());
            existingQuote.setDate(updatedQuote.getDate());
            existingQuote.setHour(updatedQuote.getHour());
            existingQuote.setComments(updatedQuote.getComments());
            existingQuote.setStatusPag(updatedQuote.getStatusPag());
            existingQuote.setStatus(updatedQuote.getStatus());

            quoteRepository.save(existingQuote);
        } else {
            throw new IllegalArgumentException("La cita con el ID proporcionado no existe.");
        }
    }

    public Page<Quote> getActiveQuotesByClientId(Long clientId, Pageable pageable) {
        return quoteRepository.findActiveQuotesByClientId(clientId, pageable);
    }

    public List<Map<String, Object>> getActiveQuotes() {
        return quoteRepository.findActiveQuotes("1");
    }

    // Total de citas activas
    public long getTotalActiveQuotes() {
        return quoteRepository.countActiveQuotes();
    }

    // Total de citas activas de hoy
    public long getTodayActiveQuotes() {
        return quoteRepository.countTodayActiveQuotes();
    }

    /*---------------------Cliente-------------------- */

    //Contenido para el citas del calendar
    // Método para obtener citas activas por ID de cliente
    public List<Map<String, Object>> getActiveQuotesByClientId(Long clientId) {
        return quoteRepository.findActiveQuotesByClientId(clientId);
    }
    

    // Total de citas activas para un cliente específico
    public long getActiveQuotesByClientID(Long clientId) {
        return quoteRepository.countActiveQuotesByClientId(clientId);
    }

    // Total de citas activas de hoy para un cliente específico
    public long getTodayActiveQuotesByClientId(Long clientId) {
        return quoteRepository.countTodayActiveQuotesByClientId(clientId);
    }

    
}
