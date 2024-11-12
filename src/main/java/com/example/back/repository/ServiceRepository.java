package com.example.back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.back.entity.ServiceEntity;

import jakarta.transaction.Transactional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    
    @Query("SELECT s FROM ServiceEntity s WHERE " +
            "(:especieId IS NULL OR s.especie.id = :especieId) AND " +
            "(:categoryId IS NULL OR s.category.id = :categoryId) AND " +
            "(:name IS NULL OR s.name LIKE %:name%)")
    Optional<List<ServiceEntity>> searchServices(
            @Param("especieId") Long especieId,
            @Param("categoryId") Long categoryId,
            @Param("name") String name);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceEntity s SET s.status = '0' WHERE s.idService = :serviceId")
    int blockService(@Param("serviceId") Long serviceId);
}
