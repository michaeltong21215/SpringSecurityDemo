package com.example.SpringSecurityDemo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequest implements Serializable {

    private static final long serialVersionUID = 1233432L;

    private String username;

    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
