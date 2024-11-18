package com.example.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Pet;

import jakarta.transaction.Transactional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
        @Query("SELECT p FROM Pet p WHERE " +
        "(:dni IS NULL OR p.client.dni = :dni) AND " +
        "(:raceName IS NULL OR p.race.name LIKE %:raceName%) AND " +
        "(:petName IS NULL OR p.name LIKE %:petName%) AND " +
        "(:status IS NULL OR p.status = :status)")
        Page<Pet> searchPets(@Param("dni") String dni,
                @Param("raceName") String raceName,
                @Param("petName") String petName,
                @Param("status") Character status,
                Pageable pageable);

    Page<Pet> findByStatus(Character status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Pet p SET p.status = '0' WHERE p.idPet = :petId")
    int blockPet(@Param("petId") Long petId);
}
