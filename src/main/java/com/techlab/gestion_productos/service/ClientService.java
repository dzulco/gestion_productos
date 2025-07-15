package com.techlab.gestion_productos.service;

import com.techlab.gestion_productos.model.Client;
import com.techlab.gestion_productos.model.Order;
import com.techlab.gestion_productos.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    
    public Client findByIdEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id " + id));
    }

    
    public List<Order> getOrdersByClientId(Long clientId) {
        Client client = findByIdEntity(clientId);
        return client.getOrders();
    }

    
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    
    public Client update(Long id, Client updatedClient) {
        Client existing = findByIdEntity(id);
        existing.setName(updatedClient.getName());
        existing.setSurname(updatedClient.getSurname());
        existing.setEmail(updatedClient.getEmail());
        return clientRepository.save(existing);
    }

    
    public void delete(Long id) {
        Client client = findByIdEntity(id);
        clientRepository.delete(client);
    }

    public boolean isEmpty() {
        return clientRepository.findAll().isEmpty();
    }
}
