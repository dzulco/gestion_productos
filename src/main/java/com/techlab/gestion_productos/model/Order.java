package com.techlab.gestion_productos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines= new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Enumerated(EnumType.STRING)
    private OrderState state;
    private Double total;


    public void addOrderLine(OrderLine line) {
        orderLines.add(line);
        line.setOrder(this); // cada vez que agrego una linea le seteo la orden por la consistencia de datos
    }

    public void removeOrderLine(OrderLine line) {
        orderLines.remove(line);
        line.setOrder(null);
    }

    public void calculateTotal() {
        this.total = orderLines.stream()
                .mapToDouble(OrderLine::getSubTotal)
                .sum();
    }
}
