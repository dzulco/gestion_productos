package com.techlab.gestion_productos.repository;

import com.techlab.gestion_productos.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
