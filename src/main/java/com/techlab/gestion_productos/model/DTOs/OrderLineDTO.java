package com.techlab.gestion_productos.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderLineDTO {
    private Long id;
    private Integer quantity;
    private Double price;
    private Double subTotal;
    private ProductDTO product;
}
