package com.security.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private boolean isAdmin;
}

