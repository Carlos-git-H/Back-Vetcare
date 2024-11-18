package com.example.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Race;
import com.example.back.repository.RaceRepository;

@Service
public class RaceService {
     @Autowired
    private RaceRepository raceRepository;

    public List<Race> searchRacesByName(String name) {
        return raceRepository.findByNameContainingIgnoreCase(name);
    }
}
