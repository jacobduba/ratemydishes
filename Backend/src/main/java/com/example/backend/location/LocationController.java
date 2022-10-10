package com.example.backend.location;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;

@RestController
@Configuration
@EnableScheduling
@RequestMapping("/location")
public class LocationController {

    @Autowired
    LocationRepository lr;
    @Autowired
    MenuRepository mr;
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
    GetSingleLocation getSingleLocation;

    @Autowired
    Locations l;

    //Finished
    @RequestMapping("/get-dining-centers")
    ArrayNode getDiningLocations() throws NoSuchFieldException, IllegalAccessException {
        return getDiningCenters.getDinCen();
    }

    //Finished
    @RequestMapping("/get-cafe")
    ArrayNode getCafeLocations() throws NoSuchFieldException, IllegalAccessException {
        return getCafes.getCafes();
    }
    //Finished
    @RequestMapping("/get-fast-casual")
    ArrayNode getFastCasualLocations() throws NoSuchFieldException, IllegalAccessException {
        return getFastCasual.getFastCas();
    }
    //Finished
    @RequestMapping("/get-convenience-store")
    ArrayNode getConvenienceStoreLocations() throws NoSuchFieldException, IllegalAccessException {
        return getConvStores.getConvStores();
    }
    //Finished
    @RequestMapping("/get-get-go")
    ArrayNode getGetGo() throws NoSuchFieldException, IllegalAccessException {
        return getGetGo.getGetGo();
    }
    //Finished
    //Request to do GET-Locations to fill Locations Database with general info including Slugs. These slugs will be used to track specific menus.
    //scheduled to run top every 30 min of every day
    @RequestMapping("/populate-db")
    @Scheduled(initialDelay=0, fixedRate=1800000)
    public void populateDB() throws Exception {
        getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/");

    }
    //In-Progress
    //run every five minutes
    @Scheduled(initialDelay=10000, fixedRate=300000)
    @RequestMapping("/menu-data")
    public void menuData() throws Exception {
        //Creating Json Object to store Location Menu
        //Grabbing list of all Location in database
        List listLoc = lr.findAll();
        ArrayNode an;
        //looping through List to extract dining centers into array node
        for (int i = 0; i < listLoc.size(); i++) {
            Object loc = listLoc.get(i);
            Class cl = loc.getClass();
            //Field of slug and converting to string
            Field slugField = cl.getDeclaredField("slug");
            slugField.setAccessible(true);
            String slugVal = (String) slugField.get(loc);
            slugVal = slugVal.substring(1, slugVal.length() - 1);

            //get current unix time stamp
            long unixTime = Instant.now().getEpochSecond();
            an = getSingleLocation.getHTML(slugVal, unixTime, "https://dining.iastate.edu/wp-json/dining/menu-hours/get-single-location/");
            getSingleLocation.populateTable(an);
        }
    }
        //To be worked on
    @RequestMapping("/get-menu/{slug}")
    @ResponseBody
    ArrayNode getMenu(@PathVariable String slug) throws Exception {
        //get current unix time stamp
        long unixTime = Instant.now().getEpochSecond();
        ArrayNode an = getSingleLocation.getHTML(slug, unixTime, "https://dining.iastate.edu/wp-json/dining/menu-hours/get-single-location/");
        //getLocations.populateTable(an);
        return an;
    }
}


