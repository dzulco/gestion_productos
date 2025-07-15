package com.techlab.gestion_productos.repository;

import com.techlab.gestion_productos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
