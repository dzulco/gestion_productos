package com.techlab.gestion_productos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "order_lines")
@Data
public class OrderLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore // para evitar el loop de JPA + spring boot al momento de serializar el entity en JSON
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("orderLines")
    private Product product;

    private Integer quantity;
    private Double subTotal;
    private Double price;
}
