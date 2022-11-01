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
public class GetGetGos {

    @Autowired
    public LocationRepository lr;

    @Autowired
    public LocationSettingService lss;

    public ArrayNode getGetGo() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Location> getLoc = lr.findByResType("[\"get-go\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < getLoc.size(); i++) {
            if (lss.getEnabled(getLoc.get(i).getTitle())) {
                ObjectNode locationNode = mapper.createObjectNode();

                locationNode.put("title", getLoc.get(i).getTitle());
                locationNode.put("slug", getLoc.get(i).getSlug());
                locationNode.set("restaurant_type", mapper.readTree(getLoc.get(i).getResType()));
                locationNode.put("facility", getLoc.get(i).getFacility());
                locationNode.set("dietary_type", mapper.readTree(getLoc.get(i).getDietType()));

                returnList.add(locationNode);
            }
        }

        return returnList;
    }
}