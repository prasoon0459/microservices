package com.example.authApp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authApp.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
