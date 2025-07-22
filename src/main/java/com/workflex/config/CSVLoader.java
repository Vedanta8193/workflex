
package com.workflex.config;

import com.opencsv.CSVReader;
import com.workflex.model.Trip;
import com.workflex.repository.TripRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;

@Component
public class CSVLoader {

    private final TripRepository tripRepository;

    public CSVLoader(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @PostConstruct
    public void loadData() {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("trip-tracking.csv")))) {
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                tripRepository.save(new Trip(null, line[0], line[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
