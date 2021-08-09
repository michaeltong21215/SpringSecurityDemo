package com.example.SpringSecurityDemo.repository;

import com.example.SpringSecurityDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByusername(String username);
}
