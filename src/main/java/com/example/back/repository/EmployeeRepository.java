package com.example.back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Employee;

import jakarta.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE " +
            "(:dni IS NULL OR e.dni = :dni) AND " +
            "(:cmvp IS NULL OR e.cmvp = :cmvp) AND " +
            "(:rolId IS NULL OR e.rol.id = :rolId) AND " +
            "(:name IS NULL OR e.firstName LIKE %:name% OR e.preName LIKE %:name%) AND " +
            "(:lastName IS NULL OR e.firstLastName LIKE %:lastName% OR e.secondLastName LIKE %:lastName%)")
    Optional<List<Employee>> searchEmployees(@Param("dni") String dni,
                                             @Param("cmvp") String cmvp,
                                             @Param("rolId") Long rolId,
                                             @Param("name") String name,
                                             @Param("lastName") String lastName);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.status = '0' WHERE e.idEmployee = :employeeId")
    int blockEmployee(@Param("employeeId") Long employeeId);
}
