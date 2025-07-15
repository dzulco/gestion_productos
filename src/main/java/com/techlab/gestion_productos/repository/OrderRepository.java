package com.techlab.gestion_productos.repository;

import com.techlab.gestion_productos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
