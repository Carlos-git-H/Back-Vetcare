package com.example.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.Pet;
import com.example.back.service.PetService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/pets")
public class PetController {
    
    @Autowired
    private PetService petService;

    @GetMapping
    public List<Pet> getAll(){
        return petService.getPets();
    }
}
