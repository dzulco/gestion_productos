package com.techlab.gestion_productos.repository;

import com.techlab.gestion_productos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long> {


}
