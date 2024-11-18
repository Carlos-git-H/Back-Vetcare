package com.example.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Category;
import com.example.back.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}
