package com.example.backend.menu;

import com.example.backend.location.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@Configuration
@EnableScheduling
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private LocationRepository lr;

    @Autowired
    private MenuRepository mr;

    @Autowired
    private GetSingleLocation getSingleLocation;

    @Autowired
    private GetMenu getMenu;

    @Autowired
    private PopCategory popCat;

    @Value("${TIME_FETCH:}") // If TIME_FETCH does not exist, set it to empty string
    private String time;

    //In-Progress
    //run every 10 minutes
    @Operation(summary = "(Karthik) This endpoint is a Scheduled Task that runs every 10 minutes on the Prod Server. It calls the Location Repository; for every location, a GET request is sent to ISU Dining to return with the current day's menu.")
    @Scheduled(initialDelay = 200, fixedRate = 600000)
    @GetMapping("/menu-data")
    public void menuData() throws Exception {
        //Delete previous vals in Repo so that I can now replace
        mr.deleteAll();
        //Creating Json Object to store Location Menu
        //Grabbing list of all Location in database
        List<Location> listLoc = lr.findAll();
        ArrayNode an;
        //looping through List to extract dining centers into array node
        for (Location loc : listLoc) {
            String slugVal = loc.getSlug();

            long unixTime;
            if (time.equals(""))
                unixTime = Instant.now().getEpochSecond();
            else
                unixTime = Long.parseLong(time);

            an = getSingleLocation.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-single-location", slugVal, unixTime);
            getSingleLocation.populateTable(an);
        }
    }

    //Finished
    //Call is to retrieve menu information from webserver. Only for locations that are open
    @Operation(summary = "(Karthik) This endpoint allows the Android Client to request a specific location's menu. It will return a complex, but organized Json Array of the specific location menu.")
    @GetMapping("/get-menu/{slug}")
    @ResponseBody
    ObjectNode getMenu(@PathVariable("slug") String slug) throws Exception {
        ObjectNode singleMenu = getMenu.returnMenu(slug);
        return singleMenu;
    }

    //Scheduled to run every 10 minutes
    @Operation(summary = "(Karthik) This endpoint is Scheduled Task running every 10 minutes on the Prod Server. It calls the Menu Repository and organizes each location menu into a parsable Json Array for the Android Client to utilize.")
    @Scheduled(initialDelay = 300, fixedRate = 600000)
    @GetMapping("/populate-categories")
    @ResponseBody
    public void getCategories() throws Exception {
        popCat.popCats();
    }

}
