package com.travels.travels.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travels.travels.models.Travel;

public interface TravelRepository extends JpaRepository<Travel, Integer> {
  List<Travel> findByUserId(Integer userId);
}
