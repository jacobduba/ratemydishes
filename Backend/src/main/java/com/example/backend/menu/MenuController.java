package com.example.backend.menu;
import com.example.backend.location.*;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    LocationRepository lr;
    @Autowired
    MenuRepository mr;

    @Autowired
    GetSingleLocation getSingleLocation;

    @Autowired
    Locations l;

    @Autowired
    GetMenu getMenu;

    @Autowired
    PopCategory popCat;



    //In-Progress
    //run every 1 minute
    @Scheduled(initialDelay=0, fixedRate=60000)
    @RequestMapping("/menu-data")
    public void menuData() throws Exception {
        //Delete previous vals in Repo so that I can now replace
        mr.deleteAll();
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
    //Finished
    //Call is to retrieve menu information from webserver. Only for locations that are open
    @RequestMapping("/get-menu/{slug}")
    @ResponseBody
    ObjectNode getMenu(@PathVariable("slug") String slug) throws Exception {
        ObjectNode singleMenu = getMenu.returnMenu(slug);
        return singleMenu;
    }
    @RequestMapping("/populate-categories")
    @ResponseBody
    ArrayNode getCategories() throws Exception {
        return popCat.popCats();
    }

}
