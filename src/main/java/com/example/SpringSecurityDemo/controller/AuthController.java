package com.example.SpringSecurityDemo.controller;

import com.example.SpringSecurityDemo.model.AuthRequest;
import com.example.SpringSecurityDemo.model.AuthResponse;
import com.example.SpringSecurityDemo.service.AuthUserDetailService;
import com.example.SpringSecurityDemo.utils.AppTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppTokenUtil appTokenUtil;

    @Autowired
    AuthUserDetailService authUserDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthRequest request){
        authenticate(request.getUsername(), request.getPassword());
        UserDetails details = authUserDetailService.loadUserByUsername(request.getUsername());
        String token = appTokenUtil.generateAppToken(details);

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
