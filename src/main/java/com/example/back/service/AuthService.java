package com.example.back.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Client;
import com.example.back.entity.Employee;
import com.example.back.entity.User;
import com.example.back.repository.ClientRepository;
import com.example.back.repository.EmployeeRepository;
import com.example.back.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Map<String, Object> authenticate(String email, String password) {
        // Buscar el usuario por email
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Verificar la contraseña (si no usas cifrado, compara directamente)
            if (user.getPassword().equals(password)) {
                Map<String, Object> response = new HashMap<>();
                response.put("email", user.getEmail());

                // Verificar si es cliente
                Optional<Client> clientOpt = clientRepository.findByUser_IdUser(user.getIdUser());
                if (clientOpt.isPresent()) {
                    response.put("type", "cliente");
                    response.put("id", clientOpt.get().getIdClient());
                    return response;
                }

                // Verificar si es empleado
                Optional<Employee> employeeOpt = employeeRepository.findByUser_IdUser(user.getIdUser());
                if (employeeOpt.isPresent()) {
                    response.put("type", "empleado");
                    response.put("id", employeeOpt.get().getIdEmployee());
                    return response;
                }

                // Si no es cliente ni empleado
                throw new IllegalArgumentException("El usuario no tiene un rol asignado.");
            }
        }

        throw new IllegalArgumentException("Correo o contraseña incorrectos.");
    }
}
