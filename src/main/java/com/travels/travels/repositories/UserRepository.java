package com.travels.travels.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travels.travels.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
