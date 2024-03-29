package com.example.backend.location;

import com.example.backend.admin.LocationSettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    public LocationSettingService lss;

    public ArrayNode getDiningCenters() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Location> diningLoc = lr.findByResType("[\"dining-center\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < diningLoc.size(); i++) {
            if (lss.getEnabled(diningLoc.get(i).getTitle())) {
                ObjectNode locationNode = mapper.createObjectNode();

                locationNode.put("title", diningLoc.get(i).getTitle());
                locationNode.put("slug", diningLoc.get(i).getSlug());
                locationNode.set("restaurant_type", mapper.readTree(diningLoc.get(i).getResType()));
                locationNode.put("facility", diningLoc.get(i).getFacility());
                locationNode.set("dietary_type", mapper.readTree(diningLoc.get(i).getDietType()));

                returnList.add(locationNode);
            }
        }

        return returnList;
    }
}