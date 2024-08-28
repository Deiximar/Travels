package com.travels.travels.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.travels.errors.ExistingEmailError;
import com.travels.errors.NotExistingEmailError;
import com.travels.errors.NotMatchCredentials;
import com.travels.travels.models.User;
import com.travels.travels.repositories.TravelRepository;
import com.travels.travels.repositories.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository, TravelRepository travelRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getUserByID(int id) {
    return userRepository.findById(id);
  }

  public List<User> getUser() {
    return userRepository.findAll();
  }

  public User addUser(User user) {
    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      throw new ExistingEmailError();
    }
    return userRepository.save(user);
  }

  public Integer login(User user) {
    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
    if (!existingUser.isPresent()) {
      throw new NotExistingEmailError();
    }
    if (existingUser.isPresent() && !existingUser.get().getPassword().equals(user.getPassword())) {
      throw new NotMatchCredentials();
    }
    return userRepository.findByEmail(user.getEmail()).get().getId();
  }
}
