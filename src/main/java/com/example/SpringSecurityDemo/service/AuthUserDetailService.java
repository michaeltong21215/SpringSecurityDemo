package com.example.SpringSecurityDemo.service;

import com.example.SpringSecurityDemo.model.MyUserPrincipal;
import com.example.SpringSecurityDemo.model.User;
import com.example.SpringSecurityDemo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AuthUserDetailService implements UserDetailsService {


    final UserRepository userRepository;

    public AuthUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByusername(userName);

        if(!ObjectUtils.isEmpty(user)) {
          return new MyUserPrincipal(user);
        }
        throw new UsernameNotFoundException(userName);
    }
}
