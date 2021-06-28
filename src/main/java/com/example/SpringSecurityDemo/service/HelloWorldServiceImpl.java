package com.example.SpringSecurityDemo.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceImpl implements HelloWorldService{

    public String helloWorld(){
        return "hello World!";
    }
}
