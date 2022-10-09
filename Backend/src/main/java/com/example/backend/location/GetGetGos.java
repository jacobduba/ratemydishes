package com.example.backend.location;

import java.lang.reflect.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGetGos {

    @Autowired
    public LocationRepository lr;

    public ArrayNode getGetGo() throws NoSuchFieldException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode GetGoLoc = mapper.createArrayNode();

        //Grabbing list of all Location in database
        List listLoc = lr.findAll();
        //looping through List to extract dining centers into array node
        for (int i = 0; i < listLoc.size(); i++) {

            //object loc represents a row and its field values
            Object loc = listLoc.get(i);
            Class cl = loc.getClass();

            //Fields of each object
            Field dietaryField = cl.getDeclaredField("dietType");
            Field facilityField = cl.getDeclaredField("facility");
            Field restaurantField = cl.getDeclaredField("resType");
            Field slugField = cl.getDeclaredField("slug");
            Field titleField = cl.getDeclaredField("title");
            //Make private fields accessible
            dietaryField.setAccessible(true);
            facilityField.setAccessible(true);
            restaurantField.setAccessible(true);
            slugField.setAccessible(true);
            titleField.setAccessible(true);

            //Values of Each field
            String dietVal = (String) dietaryField.get(loc);
            String facVal = (String) facilityField.get(loc);
            String resVal = (String) restaurantField.get(loc);
            String slugVal = (String) slugField.get(loc);
            String titleVal = (String) titleField.get(loc);

            //if the row has restype of dining center, I want to add it into the response json array.
            if (resVal.equals("[\"get-go\"]")) {
                ObjectNode locationNode = mapper.createObjectNode();

                locationNode.put("Dietary_type", dietVal);
                locationNode.put("facility", facVal);
                locationNode.put("Restaurant_type", resVal);
                locationNode.put("Slug", slugVal);
                locationNode.put("Title", titleVal);

                GetGoLoc.add(locationNode);
            }
        }
        if (GetGoLoc.isEmpty()) {
            ObjectNode locationNode = mapper.createObjectNode();

            locationNode.put("Empty-Notice", "No Get and Gos open at this time.");
            GetGoLoc.add(locationNode);
            return GetGoLoc;
        }
        return GetGoLoc;
    }
}