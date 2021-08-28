package com.example.SpringSecurityDemo.repository;

import com.example.SpringSecurityDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {
    User findByusername(String username);
}
