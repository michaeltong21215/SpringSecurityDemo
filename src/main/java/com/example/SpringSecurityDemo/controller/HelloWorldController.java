package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.repository.UserRepository;
/*
import com.example.SpringSecurityDemo.service.HelloWorldService;
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/helloworld")
@RestController
@CrossOrigin
public class HelloWorldController {

/*    @Autowired
    public HelloWorldService helloWorldService;*/
    @Autowired
    public UserRepository userRepository;

    @GetMapping
    public String getHelloWorld(@RequestParam String user){
        return userRepository.findByusername(user).getUsername();
    }

}
