package com.example.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Pet;
import com.example.back.repository.PetRepository;


@Service
public class PetService {
    
    @Autowired
    PetRepository petRepository;

    public List<Pet> getPets(){
        return petRepository.findAll();
    }
}
