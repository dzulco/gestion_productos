package com.techlab.gestion_productos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // agregar una orden para un cliente
    public void addOrder(Order order) {
        orders.add(order);
        order.setClient(this);
    }
    // remueve la una orden del cliente
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setClient(null);
    }


    // constructor sin ID
    public Client(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }


}
