package com.example.SpringSecurityDemo.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

}
