package com.techlab.gestion_productos.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    private Long id;
    private String name;
    private Integer stock;
    private Double price;
    private String description;
    private String image;
}

