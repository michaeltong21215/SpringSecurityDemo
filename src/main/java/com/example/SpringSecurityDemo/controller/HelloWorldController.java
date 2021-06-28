package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/helloworld")
@RestController
public class HelloWorldController {

    @Autowired
    public HelloWorldService helloWorldService;

    @GetMapping
    public String getHelloWorld(){
        return "hello encrypted properly!";
    }

}
