package com.travels.travels.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travels.travels.models.Travel;
import com.travels.travels.services.TravelService;

@RestController
public class TravelController {
  private final TravelService travelService;

  public TravelController(TravelService travelService) {
    this.travelService = travelService;
  }

  @GetMapping("/travels")
  public List<Travel> getTravel() {
    return travelService.getTravel();
  }
}
