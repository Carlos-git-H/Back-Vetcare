package com.example.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.ServiceEntity;
import com.example.back.repository.ServiceRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServiceEntity> getService(Long id){
        return serviceRepository.findById(id);
    }

    public void saveOrUpdate(ServiceEntity service){
        serviceRepository.save(service);
    }

    //buscar por especie, categoria,name
    public Optional<List<ServiceEntity>> searchServices(Long especieId, Long categoryId, String name) {
        return serviceRepository.searchServices(especieId, categoryId, name);
    }

    @Transactional
    public int blockService(Long serviceId) {
        return serviceRepository.blockService(serviceId);
    }
}
