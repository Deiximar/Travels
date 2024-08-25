package com.travels.travels.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travels.travels.models.Travel;
import com.travels.travels.models.User;
import com.travels.travels.repositories.TravelRepository;

@Service
public class TravelService {

  private final TravelRepository travelRepository;

  public TravelService(TravelRepository travelRepository) {
    this.travelRepository = travelRepository;
  }

  public Travel saveTravel(Travel travel) {
    return travelRepository.save(travel);
  }

  public List<Travel> getTravel() {
    return travelRepository.findAll();
  }

  public List<Travel> getTravelsByUserId(Integer userId) {
    return travelRepository.findByUserId(userId);
  }

  public Travel addTravelToUser(User user, Travel travel) {
    travel.setUser(user);
    return travelRepository.save(travel);
  }
}
