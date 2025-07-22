
package com.workflex.service;

import com.workflex.model.Trip;
import com.workflex.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip addTrip(String user, String destination) {
        return tripRepository.save(new Trip(null, user, destination));
    }

    public List<String> getRecommendations(String destination) {
        Map<String, Set<String>> destinationToUsers = new HashMap<>();
        Map<String, Set<String>> userToDestinations = new HashMap<>();

        for (Trip trip : tripRepository.findAll()) {
            destinationToUsers.computeIfAbsent(trip.getDestination(), k -> new HashSet<>()).add(trip.getUser());
            userToDestinations.computeIfAbsent(trip.getUser(), k -> new HashSet<>()).add(trip.getDestination());
        }

        Set<String> usersWhoVisited = destinationToUsers.getOrDefault(destination, Collections.emptySet());

        Map<String, Double> similarityMap = new HashMap<>();
        for (String otherDestination : destinationToUsers.keySet()) {
            if (otherDestination.equals(destination)) continue;
            Set<String> usersOther = destinationToUsers.get(otherDestination);

            int commonUsers = (int) usersOther.stream().filter(usersWhoVisited::contains).count();
            double similarity = commonUsers / (Math.sqrt(usersWhoVisited.size()) * Math.sqrt(usersOther.size()));

            if (similarity > 0) {
                similarityMap.put(otherDestination, similarity);
            }
        }

        return similarityMap.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
