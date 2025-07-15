package com.techlab.gestion_productos.model.DTOs;

public record OrderLineRequestDTO(Long productId, Integer quantity) {
}
