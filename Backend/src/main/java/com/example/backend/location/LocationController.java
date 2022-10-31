package com.example.backend.location;
import com.example.backend.menu.GetMenu;
import com.example.backend.menu.MenuRepository;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @Autowired
    GetMenu getMenu;

    //Finished
    @RequestMapping("/get-dining-centers")
    ArrayNode getDiningLocations() throws NoSuchFieldException, IllegalAccessException {
        return getDiningCenters.getDiningCenters();
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
    //scheduled to run every 10 minutes
    @RequestMapping("/populate-db")
    @Scheduled(initialDelay=100, fixedRate=600000)
    public void populateDB() throws Exception {
        getLocations.getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/");

    }


}


