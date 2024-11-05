package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
