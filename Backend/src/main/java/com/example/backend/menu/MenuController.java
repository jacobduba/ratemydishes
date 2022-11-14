package com.example.backend.menu;
import com.example.backend.location.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
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
    LocationRepository lr;
    @Autowired
    MenuRepository mr;
    @Autowired
    GetSingleLocation getSingleLocation;
    @Autowired
    Location l;
    @Autowired
    GetMenu getMenu;
    @Autowired
    PopCategory popCat;

    //In-Progress
    //run every 10 minutes
    @Scheduled(initialDelay=200, fixedRate=600000)
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

            //get current unix time stamp
            long unixTime = Instant.now().getEpochSecond();
            an = getSingleLocation.getHTML(slugVal, unixTime, "https://dining.iastate.edu/wp-json/dining/menu-hours/get-single-location/");
            getSingleLocation.populateTable(an);
        }
    }

    //Finished
    //Call is to retrieve menu information from webserver. Only for locations that are open
    @GetMapping("/get-menu/{slug}")
    @ResponseBody
    ObjectNode getMenu(@PathVariable("slug") String slug) throws Exception {
        ObjectNode singleMenu = getMenu.returnMenu(slug);
        return singleMenu;
    }

    //Scheduled to run every 10 minutes
    @Scheduled(initialDelay=300, fixedRate=600000)
    @GetMapping("/populate-categories")
    @ResponseBody
    public void getCategories() throws Exception {
        popCat.popCats();
    }

}
