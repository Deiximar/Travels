package com.travels.travels.controllers;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travels.errors.UnauthorizedException;
import com.travels.errors.UserNotFoundException;
import com.travels.travels.models.Travel;
import com.travels.travels.models.User;
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
  public ResponseEntity<Page<Travel>> getTravels(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "8") int size) {
    Pageable pageable = PageRequest.of(page, size);
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth instanceof AnonymousAuthenticationToken) {
        return ResponseEntity.ok(travelService.getPublicTravels(pageable));
      }
      User user = (User) auth.getPrincipal();
      return ResponseEntity.ok(travelService.getTravels(user.getId(), pageable));
    } catch (UnauthorizedException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  };

  @PostMapping("/travel")
  public ResponseEntity<Travel> addTravel(@RequestBody Travel travelRequest) {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      User user = (User) auth.getPrincipal();

      Travel travel = travelService.addTravelToUser(user, travelRequest);
      return ResponseEntity.ok(travel);

    } catch (UnauthorizedException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

  @GetMapping("/travel/{id}")
  public ResponseEntity<Travel> getTravel(@PathVariable Integer id) {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      User user = (User) auth.getPrincipal();
      Travel travel = travelService.getTravel(user.getId(), id);
      return ResponseEntity.ok(travel);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

}
