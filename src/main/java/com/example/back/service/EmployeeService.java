package com.example.back.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.back.entity.Employee;
import com.example.back.entity.User;
import com.example.back.repository.EmployeeRepository;
import com.example.back.repository.UserRepository;

@Service
public class EmployeeService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    
    // Obtener empleado por ID
    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    // Actualizar empleado
    public void updateEmployee(Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findById(updatedEmployee.getIdEmployee());
    
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
    
            // Actualiza los campos necesarios
            existingEmployee.setDni(updatedEmployee.getDni());
            existingEmployee.setCmvp(updatedEmployee.getCmvp());
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setPreName(updatedEmployee.getPreName());
            existingEmployee.setFirstLastName(updatedEmployee.getFirstLastName());
            existingEmployee.setSecondLastName(updatedEmployee.getSecondLastName());
            existingEmployee.setAddress(updatedEmployee.getAddress());
            existingEmployee.setCellphone(updatedEmployee.getCellphone());
            existingEmployee.setDirImage(updatedEmployee.getDirImage());
            existingEmployee.setStatus(updatedEmployee.getStatus());
    
            // Actualiza la relación con Rol si es proporcionada
            if (updatedEmployee.getRol() != null) {
                existingEmployee.setRol(updatedEmployee.getRol());
            }
    
            // Actualiza la relación con User si es proporcionada
            if (updatedEmployee.getUser() != null) {
                existingEmployee.setUser(updatedEmployee.getUser());
            }
    
            // Guarda los cambios en la base de datos
            employeeRepository.save(existingEmployee);
        } else {
            throw new IllegalArgumentException("El empleado con el ID proporcionado no existe.");
        }
    }    

    // Buscar empleados
    public Page<Employee> searchEmployees(String dni, String name, String role, Character status, Pageable pageable) {
        return employeeRepository.searchEmployees(dni, status, role, name, pageable);
    }

    // Bloquear empleado
    public boolean blockEmployee(Long employeeId) {
        int rowsUpdated = employeeRepository.blockEmployee(employeeId);
        return rowsUpdated > 0;
    }

    // Eliminar empleado
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    // Crear empleado con usuario
    public Employee createEmployeeWithUser(Employee employee) {
        // Verifica si el empleado tiene un User asociado en el JSON
        if (employee.getUser() != null) {
            // Guarda el User primero
            User savedUser = userRepository.save(employee.getUser());
            employee.setUser(savedUser);
        }
        return employeeRepository.save(employee);
    }


}
