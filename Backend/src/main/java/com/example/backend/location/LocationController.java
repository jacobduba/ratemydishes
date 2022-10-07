package com.example.backend.location;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.sql.Array;
import java.util.List;

@RestController
public class LocationController {
    @Autowired
    LocationRepository lr;
    @Autowired
    GetLocations getLocations;
    @Autowired
    GetDiningCenters getDiningCenters;

    @Autowired
    Locations l;

    @GetMapping("/get-dining-centers")
    ArrayNode getDiningLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode DiningCenters = getDiningCenters.getDinCen();//Creating New Array Node to store DiningCenter locations
        return DiningCenters;
    }

    @GetMapping("/get-cafes")
    List<Locations> getCafeLocations() {
        //todo
        return lr.findAll();
    }

    @GetMapping("/get-fast-casual")
    List<Locations> getFastCasualLocations() {
        /*lr.

        ArrayNode an = ;*/

        return lr.findAll();
    }

    //Request to do GET-Locations to fill Locations Database with general info including Slugs. These slugs will be used to track specific menus.
    @GetMapping("/populate-db")
    ArrayNode populateDB() throws Exception {
        ArrayNode an = getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/");
        return getLocations.populateTable(an);
    }
}


