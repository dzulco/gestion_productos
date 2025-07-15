package com.techlab.gestion_productos.repository;

import com.techlab.gestion_productos.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
