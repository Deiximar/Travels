package com.travels.travels.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travels.errors.ExistingEmailError;
import com.travels.travels.models.Travel;
import com.travels.travels.models.User;
import com.travels.travels.services.TravelService;
import com.travels.travels.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // Define un prefijo para las rutas
public class UserController {
    private final UserService userService;
    private final TravelService travelService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User userRequest) {
        try {
            User user = userService.addUser(userRequest);
            return ResponseEntity.ok(user);
        } catch (ExistingEmailError e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PostMapping("/{userId}/travel")
    public ResponseEntity<Travel> addTravel(@RequestBody Travel travelRequest, @PathVariable int userId) {
        return userService.getUserByID(userId)
                .map(user -> {
                    Travel travel = travelService.addTravelToUser(user, travelRequest);
                    return ResponseEntity.ok(travel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/travels")
    public ResponseEntity<List<Travel>> getUserTravels(@PathVariable int userId) {
        try {
            List<Travel> travels = travelService.getTravelsByUserId(userId);
            return ResponseEntity.ok(travels);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}/travel/{travelId}")
    public ResponseEntity<Travel> updateTravel(@PathVariable int userId, @PathVariable int travelId, @RequestBody Travel travelRequest) {
        return userService.getUserByID(userId)
                .map(user -> travelService.getTravelByIdAndUserId(travelId, userId)
                        .map(travel -> {
                            travel.setTitle(travelRequest.getTitle());
                            travel.setLocation(travelRequest.getLocation());
                            travel.setImage(travelRequest.getImage());
                            travel.setDescription(travelRequest.getDescription());
                            Travel updatedTravel = travelService.saveTravel(travel);
                            return ResponseEntity.ok(updatedTravel);
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = userService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = userService.register(request);
            return ResponseEntity.ok(response);
        } catch (ExistingEmailError e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}