package com.example.backend.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    @Autowired
    LocationRepository lr;

    @GetMapping("/get-locations/")
    List<Locations> getAllLocations() {
        return lr.findAll();
    }
}

