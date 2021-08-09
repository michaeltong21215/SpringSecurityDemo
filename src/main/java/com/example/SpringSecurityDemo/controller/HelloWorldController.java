package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.repository.UserRepository;
import com.example.SpringSecurityDemo.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/helloworld")
@RestController
public class HelloWorldController {

    @Autowired
    public HelloWorldService helloWorldService;
    @Autowired
    public UserRepository userRepository;

    @GetMapping
    public String getHelloWorld(@RequestParam String user){
        return userRepository.findByusername(user).getUsername();
    }

}
