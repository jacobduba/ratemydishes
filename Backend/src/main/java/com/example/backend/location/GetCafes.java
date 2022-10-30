package com.example.backend.location;

import com.example.backend.admin.LocationSettingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class GetCafes {

    @Autowired
    public LocationRepository lr;

    @Autowired
    public LocationSettingService lss;

    public ArrayNode getCafes() {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Locations> cafeLoc = lr.findByResType("[\"cafe\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < cafeLoc.size(); i++) {
            if (lss.getEnabled(cafeLoc.get(i).getTitle())) {
                ObjectNode locationNode = mapper.createObjectNode();

                locationNode.put("Title", cafeLoc.get(i).getTitle());
                locationNode.put("Slug", cafeLoc.get(i).getSlug());
                locationNode.put("Restaurant_type", cafeLoc.get(i).getResType());
                locationNode.put("facility", cafeLoc.get(i).getFacility());
                locationNode.put("Dietary_type", cafeLoc.get(i).getDietType());

                returnList.add(locationNode);
            }
        }

        return returnList;
    }
}