package com.example.backend.location;

import com.example.backend.menu.GetMenu;
import com.example.backend.menu.MenuRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.swagger.v3.oas.annotations.Operation;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@Configuration
@EnableScheduling
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private GetLocations getLocations;

    @Autowired
    private GetDiningCenters getDiningCenters;
    
    @Autowired
    private GetCafes getCafes;

    @Autowired
    private GetFastCasuals getFastCasual;

    @Autowired
    private GetConvStores getConvStores;

    @Autowired
    private GetGetGos getGetGo;

    @Value("${TIME_FETCH:}") // If TIME_FETCH does not exist, set it to empty string
    private String time;

    //Finished
    @Operation(summary = "(Karthik) Received from Android Client. Will return JsonArray payload of currently open Dining Centers")
    @GetMapping("/get-dining-centers")
    ArrayNode getDiningLocations() throws JsonProcessingException {
        return getDiningCenters.getDiningCenters();
    }

    //Finished
    @Operation(summary = "(Karthik) Received from Android Client. Will return JsonArray payload of currently open Cafes")
    @GetMapping("/get-cafe")
    ArrayNode getCafeLocations() throws JsonProcessingException {
        return getCafes.getCafes();
    }

    //Finished
    @Operation(summary = "(Karthik) Received from Android Client. Will return JsonArray payload of currently open Fast Casuals")
    @GetMapping("/get-fast-casual")
    ArrayNode getFastCasualLocations() throws JsonProcessingException {
        return getFastCasual.getFastCas();
    }

    //Finished
    @Operation(summary = "(Karthik) Received from Android Client. Will return JsonArray payload of currently open Convenience Stores")
    @GetMapping("/get-convenience-store")
    ArrayNode getConvenienceStoreLocations() throws JsonProcessingException {
        return getConvStores.getConvStores();
    }

    //Finished
    @Operation(summary = "(Karthik) Received from Android Client. Will return JsonArray payload of currently open Get-and-Gos")
    @GetMapping("/get-get-go")
    ArrayNode getGetGo() throws JsonProcessingException {
        return getGetGo.getGetGo();
    }

    //Finished
    //Request to do GET-Locations to fill Locations Database with general info including Slugs. These slugs will be used to track specific menus.
    //scheduled to run every 10 minutes
    @Operation(summary = "(Karthik) This endpoint is an Scheduled Task that runs every 10 minutes on the Production Server. A GET request is sent to ISU Dining to return with a JSON payload that includes currently open locations.")
    @GetMapping("/populate-db")
    @Scheduled(initialDelay = 100, fixedRate = 600000)
    public void populateDB() throws Exception {
        long unixTime;
        if (time.equals("")) {
            unixTime = Instant.now().getEpochSecond();
        } else {
            unixTime = Long.parseLong(time);
        }
        getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations", unixTime);
    }
}
