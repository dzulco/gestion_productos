package com.techlab.gestion_productos.controller;

import com.techlab.gestion_productos.model.Client;
import com.techlab.gestion_productos.model.DTOs.ClientDTO;
import com.techlab.gestion_productos.model.DTOs.OrderDTO;
import com.techlab.gestion_productos.service.ClientService;
import com.techlab.gestion_productos.service.DtoConverterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DtoConverterService converter;

    // GET /clients
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients().stream()
                .map(converter::toClientDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }

    // GET /clients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        try {
            Client client = clientService.findByIdEntity(id);
            return ResponseEntity.ok(converter.toClientDTO(client));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /clients/{id}/orders
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByClient(@PathVariable Long id) {
        try {
            List<OrderDTO> orders = clientService.getOrdersByClientId(id).stream()
                    .map(converter::toOrderDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orders);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /clients
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody Client client) {
        Client saved = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.toClientDTO(saved));
    }

    // PUT /clients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        try {
            Client saved = clientService.update(id, updatedClient);
            return ResponseEntity.ok(converter.toClientDTO(saved));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /clients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

