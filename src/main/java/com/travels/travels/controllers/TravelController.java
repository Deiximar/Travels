package com.travels.travels.controllers;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travels.travels.models.Travel;
import com.travels.travels.services.TravelService;

@RequestMapping("/api")
@RestController
public class TravelController {
  private final TravelService travelService;

  public TravelController(TravelService travelService) {
    this.travelService = travelService;
  }

  @GetMapping("/travels")
  public Page<Travel> getTravels(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "") int size,@RequestHeader int token) {
    int userId = token;
    Pageable pageable = PageRequest.of(page, size);
    return travelService.getTravels(userId, pageable);
  }

  @DeleteMapping("/travels/{id}")
  public ResponseEntity<String> deleteTravel(@PathVariable int id) {
    try {
      travelService.deleteTravel(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @GetMapping("/auth/{userId}/travels/{travelId}")
  public ResponseEntity<Travel> getTravel(@PathVariable Integer userId, @PathVariable Integer travelId) {
    try {
      Travel travel = travelService.getTravel(userId, travelId);
      return ResponseEntity.ok(travel);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @GetMapping("/travels/search")
  public Page<Travel> getSearchTravels(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "") int size, @RequestParam String searchTerm ,@RequestHeader int token) {
    int userId = token;
    Pageable pageable = PageRequest.of(page, size);
    return travelService.getSearchTravels(userId, pageable, searchTerm);
  }

}
