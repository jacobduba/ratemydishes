package com.example.backend.location;

import java.lang.reflect.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetFastCasuals {

    @Autowired
    public LocationRepository lr;

    public ArrayNode getFastCas() throws NoSuchFieldException, IllegalAccessException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Locations> fastLoc = lr.findByResType("[\"fast-casual\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < fastLoc.size(); i++) {
            ObjectNode locationNode = mapper.createObjectNode();

            locationNode.put("Title", fastLoc.get(i).getTitle());
            locationNode.put("Slug", fastLoc.get(i).getSlug());
            locationNode.put("Restaurant_type", fastLoc.get(i).getResType());
            locationNode.put("facility", fastLoc.get(i).getFacility());
            locationNode.put("Dietary_type", fastLoc.get(i).getDietType());

            returnList.add(locationNode);
        }

        return returnList;
    }
}