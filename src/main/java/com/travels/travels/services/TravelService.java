package com.travels.travels.services;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<Travel> getTravels(int userId, Pageable pageable) {
    return travelRepository.findAllByUserIdFirst(userId, pageable);
  }

  public List<Travel> getTravelsByUserId(Integer userId) {
    return travelRepository.findByUserId(userId);
  }

  public Travel addTravelToUser(User user, Travel travel) {
    travel.setUser(user);
    return travelRepository.save(travel);
  }

  public Optional<Travel> getTravelByIdAndUserId(int travelId, int userId) {
    return travelRepository.findByIdAndUserId(travelId, userId);
  }

  public void deleteTravel(int id) {
    Optional<Travel> travel = travelRepository.findById(id);
    if (travel.isPresent()) {
      travelRepository.deleteById(id);
    } else {
      throw new RuntimeException("This trip does not exist ");
    }
  }

  public Travel getTravel(Integer userId, Integer travelId) {
    try {
      Optional<Travel> existingTravel = getTravelByIdAndUserId(travelId, userId);
      Travel travel = existingTravel.get();
      return travel;

    } catch (Error er) {
      throw new RuntimeErrorException(er);
    }
  }
}
