package com.example.backend.location;

import com.example.backend.admin.LocationSettingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class GetConvStores {

    @Autowired
    public LocationRepository lr;

    @Autowired
    public LocationSettingService lss;

    public ArrayNode getConvStores() {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Locations> convLoc = lr.findByResType("[\"convenience-store\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < convLoc.size(); i++) {
            if (lss.getEnabled(convLoc.get(i).getTitle())) {
                ObjectNode locationNode = mapper.createObjectNode();

                locationNode.put("Title", convLoc.get(i).getTitle());
                locationNode.put("Slug", convLoc.get(i).getSlug());
                locationNode.put("Restaurant_type", convLoc.get(i).getResType());
                locationNode.put("facility", convLoc.get(i).getFacility());
                locationNode.put("Dietary_type", convLoc.get(i).getDietType());

                returnList.add(locationNode);
            }
        }

        return returnList;
    }
}