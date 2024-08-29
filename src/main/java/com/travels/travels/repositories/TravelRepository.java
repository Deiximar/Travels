package com.travels.travels.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.travels.travels.models.Travel;

public interface TravelRepository extends JpaRepository<Travel, Integer> {
  List<Travel> findByUserId(Integer userId);

  Optional<Travel> findByIdAndUserId(Integer travelId, Integer userId);

  @Query("SELECT t FROM Travel t ORDER BY CASE WHEN t.user.id = :userId THEN 0 ELSE 1 END, t.id")
  Page<Travel> findAllByUserIdFirst(int userId, Pageable pageable);
}
