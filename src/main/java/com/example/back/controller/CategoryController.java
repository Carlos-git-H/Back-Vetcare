package com.example.back.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.back.entity.Category;
import com.example.back.service.CategoryService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/search")
    public ResponseEntity<?> searchCategories(@RequestParam String name) {
        List<Category> categories = categoryService.searchCategoriesByName(name);
        if (categories.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "content", List.of(),
                "message", "No se encontraron categorías que coincidan con el criterio de búsqueda."
            ));
        }
        return ResponseEntity.ok(Map.of(
            "content", categories,
            "message", "Categorías encontradas con éxito."
        ));
    }
}

