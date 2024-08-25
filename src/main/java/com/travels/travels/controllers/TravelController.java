package com.travels.travels.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travels.travels.models.Travel;
import com.travels.travels.services.TravelService;
import com.travels.travels.services.UserService;

@RestController
public class TravelController {
  private final TravelService travelService;
  private final UserService userService;

  public TravelController(TravelService travelService, UserService userService) {
    this.travelService = travelService;
    this.userService = userService;
  }

  @PostMapping("/travels/{userId}")
  public ResponseEntity<Travel> addTravel(@RequestBody Travel travelRequest, @PathVariable int userId) {

    return userService.getUserByID(userId).map(user -> {
      travelRequest.setUser(user);
      Travel travel = travelService.saveTravel(travelRequest);
      return ResponseEntity.ok(travel);
    }).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/travels")
  public List<Travel> getTravel() {
    return travelService.getTravel();
  }
}
