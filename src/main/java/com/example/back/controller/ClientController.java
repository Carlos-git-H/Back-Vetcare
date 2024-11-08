package com.example.back.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.Client;
import com.example.back.service.ClientService;



@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    @Autowired
    private ClientService clientService;


    @GetMapping
    public List<Client> getAll(){
        return clientService.getClients();
    }

    @GetMapping("/{clientId}")
    public Optional<Client> getById(@PathVariable("clientId") Long clientId){
        return clientService.getClient(clientId);
    }


        /* 
    @PostMapping
    public void saveUpdate(@RequestBody Client client){
        clientService.saveOrUpdate(client);
    }
        */

    @DeleteMapping("/{clientId}")
    public void delete(@PathVariable("clientId") Long clientId){
        clientService.delete(clientId);
    }

    
    @GetMapping("/search")
    public Optional<List<Client>> searchClients(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String cellphone,
            @RequestParam(required = false) String email) {

        return clientService.searchClients(dni, name, lastName, cellphone, email);
    }

    @PostMapping
    public ResponseEntity<Client> createClientWithUser(@RequestBody Client client) {
        try {
            // Llama al servicio para crear el cliente y su usuario
            Client savedClient = clientService.createClientWithUser(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Manejo de errores de validación
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de errores generales
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
