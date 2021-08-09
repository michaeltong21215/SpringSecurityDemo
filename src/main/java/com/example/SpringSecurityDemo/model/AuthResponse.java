package com.example.SpringSecurityDemo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponse implements Serializable {

    private static final long serialVersionUID = 1233432L;

    private String jwttoken;

    public AuthResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
