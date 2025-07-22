
package com.workflex.controller;

import com.workflex.dto.TripRequestDTO;
import com.workflex.model.Trip;
import com.workflex.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflex")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/trip")
    public Trip bookTrip(@RequestBody TripRequestDTO request) {
        return tripService.addTrip(request.getUser(), request.getDestination());
    }

    @GetMapping("/destination/recommendations")
    public List<String> getRecommendations(@RequestParam String destination) {
        return tripService.getRecommendations(destination);
    }
}
