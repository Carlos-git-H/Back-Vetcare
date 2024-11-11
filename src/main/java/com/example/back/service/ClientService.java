package com.example.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.entity.Client;
import com.example.back.entity.User;
import com.example.back.repository.ClientRepository;
import com.example.back.repository.UserRepository;



@Service
public class ClientService {
    

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public Optional<Client> getClient(Long id){
        return clientRepository.findById(id);
    }

    public void saveOrUpdate(Client client){
        clientRepository.save(client);
    }

    public void delete(Long id){
        clientRepository.deleteById(id);
    }

    //buscar cliente
    public Optional<List<Client>> searchClients(String dni, String name, String lastName, String cellphone, String email) {
        return clientRepository.searchClients(dni, name, lastName, cellphone, email);
    }

    public Client createClientWithUser(Client client) {
        // Verifica si el cliente tiene un User asociado en el JSON
        if (client.getUser() != null) {
            // Guarda el User primero
            User savedUser = userRepository.save(client.getUser());
            client.setUser(savedUser);
        }
        return clientRepository.save(client);
    }


    public boolean blockClient(Long clientId) {
        int rowsUpdated = clientRepository.blockClient(clientId);
        return rowsUpdated > 0;
    }
    
}
