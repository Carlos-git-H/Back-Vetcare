package com.example.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="VC_TBCA")
public class Category {
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idCategory;

    @Column(nullable = false)
    private String name;
    
}
