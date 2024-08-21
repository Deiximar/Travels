package com.travels.travels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travels.travels.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
