package com.travels.travels.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.travels.travels.models.Travel;
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

public void deleteTravel(int id) {
  Optional<Travel> travel = travelRepository.findById(id);
  if (travel.isPresent()) {
      travelRepository.deleteById(id);
  } else {
      throw new RuntimeException("This trip does not exist ");
  }
}
}
