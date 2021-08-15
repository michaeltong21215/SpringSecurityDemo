package com.example.SpringSecurityDemo.validation;

import com.example.SpringSecurityDemo.exception.InvalidUserException;
import com.example.SpringSecurityDemo.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UserValidation {
    public void validate(UserDto user) throws InvalidUserException {
        if(ObjectUtils.isEmpty(user.getPassword()) && ObjectUtils.isEmpty(user.getUsername())){
            throw new InvalidUserException("Creating a new user requires both username and password");
        }
    }
}
