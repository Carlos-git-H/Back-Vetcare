package com.example.back.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.Employee;
import com.example.back.service.EmployeeService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public Optional<Employee> getById(@PathVariable("employeeId") Long employeeId){
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee) {
        employeeService.saveOrUpdate(employee);
        return ResponseEntity.ok( "Empleado actualizado exitosamente.");
    }   

    @GetMapping("/search")
    public Optional<List<Employee>> searchEmployees(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String cmvp,
            @RequestParam(required = false) Long rolId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName) {

        return employeeService.searchEmployees(dni, cmvp, rolId, name, lastName);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployeeWithUser(@RequestBody Employee employee) {
        try {
            // Llama al servicio para crear el cliente y su usuario
            Employee savedEmployee = employeeService.createEmployeeWithUser(employee);
            return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Manejo de errores de validación
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores generales
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{employeeId}/block")
    public ResponseEntity<String> blockEmployee(@PathVariable Long employeeId) {
        boolean isBlocked = employeeService.blockEmployee(employeeId);
        
        if (isBlocked) {
            return ResponseEntity.ok("Empleado Bloqueado exitosamente.");
        } else {
            return ResponseEntity.status(400).body("Error al bloquear al empleado.");
        }
    }

}
