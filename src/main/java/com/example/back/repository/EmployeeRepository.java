package com.example.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "(:dni IS NULL OR e.dni LIKE %:dni%) AND " +
            "(:status IS NULL OR e.status = :status) AND " +
            "(:role IS NULL OR e.role.name LIKE %:role%) AND " +
            "(:name IS NULL OR (e.firstName LIKE %:name% OR e.lastName LIKE %:name%))")
    Page<Employee> searchEmployees(
            @Param("dni") String dni,
            @Param("status") Character status,
            @Param("role") String role,
            @Param("name") String name,
            Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.status = '0' WHERE e.id = :employeeId")
    int blockEmployee(@Param("employeeId") Long employeeId);
}
