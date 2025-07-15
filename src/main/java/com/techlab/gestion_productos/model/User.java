package com.techlab.gestion_productos.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
   /* @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;*/
}
