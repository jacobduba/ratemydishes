package com.example.backend.location;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;
import java.util.List;

@RestController
@Configuration
@EnableScheduling
public class LocationController {

    @Autowired
    LocationRepository lr;
    @Autowired
    GetLocations getLocations;
    @Autowired
    GetDiningCenters getDiningCenters;
    @Autowired
    GetCafes getCafes;
    @Autowired
    GetFastCasuals getFastCasual;
    @Autowired
    GetConvStores getConvStores;
    @Autowired
    GetGetGos getGetGo;

    @Autowired
    Locations l;

    @RequestMapping("location")

    @GetMapping("/get-dining-centers")
    ArrayNode getDiningLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode DiningCenters = getDiningCenters.getDinCen();//Creating New Array Node to store DiningCenter locations
        return DiningCenters;
    }

    @GetMapping("/get-cafe")
    ArrayNode getCafeLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode Cafes = getCafes.getCafes();
        return Cafes;
    }

    @GetMapping("/get-fast-casual")
    ArrayNode getFastCasualLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode FastCasuals = getFastCasual.getFastCas();
        return FastCasuals;
    }
    @GetMapping("/get-convenience-store")
    ArrayNode getConvenienceStoreLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode ConvStores = getConvStores.getConvStores();
        return ConvStores;
    }
    @GetMapping("/get-get-go")
    ArrayNode getGetGo() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode GetGo = getGetGo.getGetGo();
        return GetGo;
    }

    //Request to do GET-Locations to fill Locations Database with general info including Slugs. These slugs will be used to track specific menus.
    //scheduled to run top every hour of every day
    @Scheduled(cron = "0 0 * * * *")
    @GetMapping("/populate-db")
    ArrayNode populateDB() throws Exception {
        ArrayNode an = getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/");
        getLocations.populateTable(an);
        return an;
    }
}


