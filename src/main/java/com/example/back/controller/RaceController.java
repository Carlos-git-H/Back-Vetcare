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

import com.example.back.entity.Race;
import com.example.back.service.RaceService;


@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/races")
public class RaceController {
    @Autowired
    private RaceService raceService;

    @GetMapping("/search")
    public ResponseEntity<?> searchRaces(@RequestParam String name) {
        List<Race> races = raceService.searchRacesByName(name);
        if (races.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "content", List.of(),
                "message", "No se encontraron razas que coincidan con el criterio de búsqueda."
            ));
        }
        return ResponseEntity.ok(Map.of(
            "content", races,
            "message", "Razas encontradas con éxito."
        ));
    }
}
