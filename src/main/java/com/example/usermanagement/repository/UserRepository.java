package com.example.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.usermanagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
