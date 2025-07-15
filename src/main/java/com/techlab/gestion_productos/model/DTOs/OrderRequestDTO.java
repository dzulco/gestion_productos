package com.techlab.gestion_productos.model.DTOs;

import java.util.List;

public record OrderRequestDTO(Long clientId, List<OrderLineRequestDTO> lineRequestDTOList) {
}
