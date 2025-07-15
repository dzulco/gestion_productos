package com.techlab.gestion_productos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer stock;
    private Double price;
    private String description;
    private String image;

    public Product(String name, Integer stock, Double price, String description, String image) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product() {}
}
