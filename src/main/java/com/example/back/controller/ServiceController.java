package com.example.back.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.Category;
import com.example.back.entity.Especie;
import com.example.back.entity.ServiceEntity;
import com.example.back.service.ServiceService;


@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    ServiceService serviceService;

    @GetMapping
    public List<ServiceEntity> getAll(){
        return serviceService.getServices();
    }

    @GetMapping("/{serviceId}")
    public Optional<ServiceEntity> getbyId(@PathVariable("serviceId") Long serviceId){
        return serviceService.getService(serviceId);
    }

    /*
@PutMapping("/update/{serviceId}")
public ResponseEntity<String> updateService(@PathVariable Long serviceId, @RequestBody Map<String, Object> serviceDetails) {
    Optional<ServiceEntity> existingServiceOpt = serviceService.getService(serviceId);

    if (existingServiceOpt.isPresent()) {
        ServiceEntity existingService = existingServiceOpt.get();

        // Actualiza los campos del objeto existente
        existingService.setName((String) serviceDetails.get("name"));
        existingService.setDescription((String) serviceDetails.get("description"));
        existingService.setRecommendedAge((String) serviceDetails.get("recommendedAge"));
        existingService.setRecommendedFrequency((String) serviceDetails.get("recommendedFrequency"));
        existingService.setPrice(Double.parseDouble(serviceDetails.get("price").toString()));
        existingService.setDirImage((String) serviceDetails.getOrDefault("dirImage", "ServiceFoto.png"));
        existingService.setStatus(((String) serviceDetails.get("status")).charAt(0));

        // Asignar Especie y Category usando sus IDs
        Long especieId = Long.valueOf(serviceDetails.get("especieId").toString());
        Long categoryId = Long.valueOf(serviceDetails.get("categoryId").toString());

        // Obtiene las entidades relacionadas y las asigna
        Especie especie = especieRepository.findById(especieId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (especie != null) {
            existingService.setEspecie(especie);
        }
        if (category != null) {
            existingService.setCategory(category);
        }

        // Guarda los cambios
        serviceService.saveOrUpdate(existingService);

        return ResponseEntity.ok("Servicio actualizado exitosamente.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado.");
    }
}
    */
    @PutMapping("/block/{serviceId}")
    public ResponseEntity<String> blockService(@PathVariable("serviceId") Long serviceId) {
        int updatedRows = serviceService.blockService(serviceId);
        if (updatedRows > 0) {
            return ResponseEntity.ok("Servicio Bloqueado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al Bloquear.");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveService(@RequestBody ServiceEntity service) {
        try {
            serviceService.saveOrUpdate(service);
            return ResponseEntity.ok("Servicio guardado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el servicio.");
        }
    }
}
