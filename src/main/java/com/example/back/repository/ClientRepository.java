package com.example.back.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Client;

import jakarta.transaction.Transactional;

@Repository
public interface ClientRepository extends  JpaRepository<Client, Long>
{

        Page<Client> findByStatus(Character status, Pageable pageable);


        @Query("SELECT c FROM Client c WHERE " +
       "(:dni IS NULL OR c.dni = :dni) AND " +
       "(:name IS NULL OR c.firstName LIKE %:name% OR c.preName LIKE %:name%) AND " +
       "(:lastName IS NULL OR c.firstLastName LIKE %:lastName% OR c.secondLastName LIKE %:lastName%) AND " +
       "(:status IS NULL OR c.status = :status)")
        Page<Client> searchClients(@Param("dni") String dni,
                           @Param("name") String name,
                           @Param("lastName") String lastName,
                           @Param("status") Character status,
                           Pageable pageable);


        @Transactional
        @Modifying
        @Query("UPDATE Client c SET c.status = '0' WHERE c.idClient = :clientId")
        int blockClient(@Param("clientId") Long clientId);

        //buscar para el nuevo pet
        Optional<Client> findByDni(String dni);

        Optional<Client> findByUser_IdUser(Long userId);

}
