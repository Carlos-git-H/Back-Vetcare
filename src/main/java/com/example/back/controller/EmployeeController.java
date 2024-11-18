package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.back.entity.Employee;
import com.example.back.service.EmployeeService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("http://localhost:5173")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Obtener empleado por ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        Optional<Employee> employee = employeeService.getEmployee(employeeId);

        // Si el empleado está presente, retorna el objeto. Si no, retorna un error 404.
        return employee.isPresent()
            ? ResponseEntity.ok(employee.get())
            : ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("El empleado con el ID proporcionado no existe.");
    }

    // Crear empleado
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployeeWithUser(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el empleado.");
        }
    }

    // Actualizar empleado
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee) {
        try {
            employee.setIdEmployee(employeeId);
            employeeService.updateEmployee(employee);
            return ResponseEntity.ok("Empleado actualizado exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el empleado.");
        }
    }

    // Buscar empleados con filtros
    @GetMapping("/search")
    public ResponseEntity<?> searchEmployees(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Character status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.searchEmployees(dni, name, role, status, pageable);

        if (employees.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "content", employees.getContent(),
                    "message", "No se encontraron empleados con los criterios proporcionados."
            ));
        }

        return ResponseEntity.ok(Map.of(
                "content", employees.getContent(),
                "totalPages", employees.getTotalPages(),
                "message", "Empleados encontrados con éxito."
        ));
    }

    // Bloquear empleado
    @PutMapping("/{employeeId}/block")
    public ResponseEntity<?> blockEmployee(@PathVariable Long employeeId) {
        boolean isBlocked = employeeService.blockEmployee(employeeId);
        if (isBlocked) {
            return ResponseEntity.ok("Empleado bloqueado con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El empleado con el ID proporcionado no existe o ya fue bloqueado.");
        }
    }

    // Eliminar empleado
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        try {
            employeeService.delete(employeeId);
            return ResponseEntity.ok("Empleado eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el empleado.");
        }
    }
}
