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
@RequestMapping("location")
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


    @GetMapping("get-dining-centers")
    ArrayNode getDiningLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode DiningCenters = getDiningCenters.getDinCen();//Creating New Array Node to store DiningCenter locations
        return DiningCenters;
    }


    @GetMapping("get-cafe")
    ArrayNode getCafeLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode Cafes = getCafes.getCafes();
        return Cafes;
    }

    @GetMapping("get-fast-casual")
    ArrayNode getFastCasualLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode FastCasuals = getFastCasual.getFastCas();
        return FastCasuals;
    }

    @GetMapping("get-convenience-store")
    ArrayNode getConvenienceStoreLocations() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode ConvStores = getConvStores.getConvStores();
        return ConvStores;
    }

    @GetMapping("get-get-go")
    ArrayNode getGetGo() throws NoSuchFieldException, IllegalAccessException {
        ArrayNode GetGo = getGetGo.getGetGo();
        return GetGo;
    }

    //Request to do GET-Locations to fill Locations Database with general info including Slugs. These slugs will be used to track specific menus.
    //scheduled to run top every hour of every day
    @Scheduled(cron = "0 0 * * * *")
    @GetMapping("populate-db")
    ArrayNode populateDB() throws Exception {
        ArrayNode an = getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/");
        getLocations.populateTable(an);
        return an;
    }

    //run every five minutes
    @Scheduled(cron = "0 0/5 * * * *")
    @GetMapping("menu-data")
    ArrayNode menuData() throws Exception {
        //Creating Json Object to store Location Menu
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode locMenu = mapper.createArrayNode();
        //Grabbing list of all Location in database
        List listLoc = lr.findAll();
        ArrayNode an = null;
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
        return an;
    }

    @GetMapping("get-menu/{slug}")
    @ResponseBody
    ArrayNode getMenu(@PathVariable String slug) throws Exception {
        //get current unix time stamp
        long unixTime = Instant.now().getEpochSecond();
        ArrayNode an = getSingleLocation.getHTML(slug, unixTime, "https://dining.iastate.edu/wp-json/dining/menu-hours/get-single-location/");
        getLocations.populateTable(an);
        return an;
    }
}


