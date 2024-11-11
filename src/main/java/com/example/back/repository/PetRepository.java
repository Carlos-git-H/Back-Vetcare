package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
}
