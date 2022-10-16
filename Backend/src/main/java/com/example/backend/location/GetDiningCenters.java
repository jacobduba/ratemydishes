package com.example.backend.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class GetDiningCenters {

    @Autowired
    public LocationRepository lr;

    public ArrayNode getDiningCenters() throws NoSuchFieldException, IllegalAccessException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Locations> diningLoc = lr.findByResType("[\"dining-center\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < diningLoc.size(); i++) {
            ObjectNode locationNode = mapper.createObjectNode();

            locationNode.put("Title", diningLoc.get(i).getTitle());
            locationNode.put("Slug", diningLoc.get(i).getSlug());
            locationNode.put("Restaurant_type", diningLoc.get(i).getResType());
            locationNode.put("facility", diningLoc.get(i).getFacility());
            locationNode.put("Dietary_type", diningLoc.get(i).getDietType());

            returnList.add(locationNode);
        }

        return returnList;
    }
}