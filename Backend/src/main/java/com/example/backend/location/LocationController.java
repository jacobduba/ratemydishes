package com.example.backend.location;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    @Autowired
    LocationRepository lr;
    @Autowired
    GetLocations getLocations;

    @GetMapping("/get-dining-centers")
    List<Locations> getDiningLocations() {
        //todo
        return lr.findAll();
    }

    @GetMapping("/get-cafes")
    List<Locations> getCafeLocations() {
        //todo
        return lr.findAll();
    }

    @GetMapping("/get-fast-casual")
    List<Locations> getAllLocations() {
        //todo
        return lr.findAll();
    }

    @GetMapping("/populate-db")
    ArrayNode populateDB() throws Exception {
        ArrayNode an = getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/");
        return getLocations.populateTable(an);
    }
}


