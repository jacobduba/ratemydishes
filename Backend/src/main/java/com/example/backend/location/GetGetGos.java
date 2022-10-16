package com.example.backend.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetGetGos {

    @Autowired
    public LocationRepository lr;

    public ArrayNode getGetGo() throws NoSuchFieldException, IllegalAccessException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Locations> getLoc = lr.findByResType("[\"get-go\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < getLoc.size(); i++) {
            ObjectNode locationNode = mapper.createObjectNode();

            locationNode.put("Title", getLoc.get(i).getTitle());
            locationNode.put("Slug", getLoc.get(i).getSlug());
            locationNode.put("Restaurant_type", getLoc.get(i).getResType());
            locationNode.put("facility", getLoc.get(i).getFacility());
            locationNode.put("Dietary_type", getLoc.get(i).getDietType());

            returnList.add(locationNode);
        }

        return returnList;
    }
}