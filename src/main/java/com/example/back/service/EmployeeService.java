package com.example.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployee(Long id){
        return employeeRepository.findById(id);
    }

    public void saveOrUpdate(Employee employee){
        employeeRepository.save(employee);
    }

    public Optional<List<Employee>> searchEmployees(String dni, String cmvp, Long rolId, String name, String lastName) {
        return employeeRepository.searchEmployees(dni, cmvp, rolId, name, lastName);
    }
    
    public boolean blockEmployee(Long employeeId) {
        int rowsUpdated = employeeRepository.blockEmployee(employeeId);
        return rowsUpdated > 0;
    }


        public Employee createEmployeeWithUser(Employee employee) {
        // Verifica si el cliente tiene un User asociado en el JSON
        if (employee.getUser() != null) {
            // Guarda el User primero
            User savedUser = userRepository.save(employee.getUser());
            employee.setUser(savedUser);
        }
        return employeeRepository.save(employee);
    }


}
