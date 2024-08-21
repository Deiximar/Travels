package com.travels.travels.services;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.travels.travels.models.Travel;
import com.travels.travels.models.User;
import com.travels.travels.repositories.TravelRepository;
import com.travels.travels.repositories.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final TravelRepository travelRepository;

  public UserService(UserRepository userRepository, TravelRepository travelRepository) {
    this.userRepository = userRepository;
    this.travelRepository = travelRepository;
  }

  public Optional<User> getUserByID(int id) {
    return userRepository.findById(id);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  // public ResponseEntity<Object> addUser(User user) {
  // saveUser(user);
  // return new ResponseEntity<>(user, HttpStatus.CREATED);
  // }

  public User addUser(int user_id, Travel travel) {
    Optional<User> optionalUser = userRepository.findById(user_id);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      travel.setUser(user);
      user.getTravels().add(travel);
      return userRepository.save(user);
    } else {
      throw new RuntimeException("User not found with id: " + user_id);
    }
  }

  public User addTravelToUser(int user_id, Travel travel) {
    Optional<User> optionalUser = userRepository.findById(user_id);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      travel.setUser(user);
      travelRepository.save(travel);
      user.getTravels().add(travel);
      return userRepository.save(user);
    } else {
      throw new RuntimeException("User not found with id: " + user_id);
    }
  }

}
