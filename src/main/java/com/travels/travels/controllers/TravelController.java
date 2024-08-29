package com.travels.travels.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
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

import com.travels.errors.UnauthorizedException;
import com.travels.errors.UserNotFoundException;
import com.travels.travels.models.Travel;
import com.travels.travels.services.TravelService;
import com.travels.travels.services.UserService;

@RequestMapping("/api")
@RestController
public class TravelController {
  private final TravelService travelService;
  private final UserService userService;

  public TravelController(TravelService travelService, UserService userService) {
    this.travelService = travelService;
    this.userService = userService;
  }

  @GetMapping("/travels")
  public ResponseEntity<?> getTravels(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "8") int size, @RequestHeader(required = false) String token) {
    Pageable pageable = PageRequest.of(page, size);
    if (token != null && !token.isEmpty()) {
      try {
        int userId = userService.getUserId(token);
        return ResponseEntity.ok(travelService.getTravels(userId, pageable));
      } catch (UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
      } catch (UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
    } else {
      return ResponseEntity.ok(travelService.getPublicTravels(pageable));
    }
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

}
