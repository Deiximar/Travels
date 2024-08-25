package com.travels.travels.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travels.travels.models.Travel;
import com.travels.travels.models.User;
import com.travels.travels.services.TravelService;
import com.travels.travels.services.UserService;

@RestController
public class UserController {

  private final UserService userService;
  private final TravelService travelService;

  public UserController(UserService userService, TravelService travelService) {
    this.userService = userService;
    this.travelService = travelService;
  }

  @PostMapping("/users")
  public ResponseEntity<User> addUser(@RequestBody User userRequest) {
    User user = userService.saveUser(userRequest);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/user/{userId}/travel")
  public ResponseEntity<Travel> addTravel(@RequestBody Travel travelRequest, @PathVariable int userId) {
    return userService.getUserByID(userId).map(user -> {
      Travel travel = travelService.addTravelToUser(user, travelRequest);
      return ResponseEntity.ok(travel);
    }).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/user/{userId}/travels")
  public ResponseEntity<List<Travel>> getUserTravels(@PathVariable int userId) {
    try {
      List<Travel> travels = travelService.getTravelsByUserId(userId);
      return ResponseEntity.ok(travels);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
