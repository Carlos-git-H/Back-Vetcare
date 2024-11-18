package com.example.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Especie;
import com.example.back.repository.EspecieRepository;

@Service
public class EspecieService {
    
    @Autowired
    private EspecieRepository especieRepository;

    public List<Especie> searchEspeciesByName(String name) {
        return especieRepository.findByNameContainingIgnoreCase(name);
    }
}
