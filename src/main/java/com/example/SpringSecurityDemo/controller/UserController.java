package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.exception.InvalidUserException;
import com.example.SpringSecurityDemo.model.UserDto;
import com.example.SpringSecurityDemo.service.AuthUserDetailService;
import com.example.SpringSecurityDemo.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    AuthUserDetailService authUserDetailService;
    
    @Autowired
    UserValidation userValidation;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto user) throws InvalidUserException {
        userValidation.validate(user);
        return new ResponseEntity<>(authUserDetailService.registerUser(user), HttpStatus.CREATED);
    }
}
