package com.travels.travels.controllers;

import org.springframework.http.ResponseEntity;
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

  @PostMapping("/{user_id}/travels")
  public ResponseEntity<User> addTravelToUser(@PathVariable int user_id, @RequestBody Travel travelRequest) {
    try {
      User user = userService.addTravelToUser(user_id, travelRequest);
      return ResponseEntity.ok(user);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
