package com.techlab.gestion_productos.controller;

import com.techlab.gestion_productos.model.DTOs.OrderDTO;
import com.techlab.gestion_productos.model.DTOs.OrderRequestDTO;
import com.techlab.gestion_productos.model.Order;
import com.techlab.gestion_productos.service.DtoConverterService;
import com.techlab.gestion_productos.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DtoConverterService dtoConverterService;

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> dtos = orderService.getAllOrders().stream()
                .map(dtoConverterService::toOrderDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(dtoConverterService::toOrderDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = dtoConverterService.fromOrderRequestDTO(orderRequestDTO);
        Order savedOrder = orderService.createOrder(order);
        OrderDTO dto = dtoConverterService.toOrderDTO(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            Order updated = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
