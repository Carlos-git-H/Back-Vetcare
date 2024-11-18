package com.example.back.controller;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.Especie;
import com.example.back.service.EspecieService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    @Autowired
    private EspecieService especieService;

    @GetMapping("/search")
    public ResponseEntity<?> searchEspecies(@RequestParam String name) {
        List<Especie> especies = especieService.searchEspeciesByName(name);
        if (especies.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "content", List.of(),
                "message", "No se encontraron especies que coincidan con el criterio de búsqueda."
            ));
        }
        return ResponseEntity.ok(Map.of(
            "content", especies,
            "message", "Especies encontradas con éxito."
        ));
    }
}

