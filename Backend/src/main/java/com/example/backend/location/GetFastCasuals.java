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
public class GetFastCasuals {

    @Autowired
    public LocationRepository lr;

    @Autowired
    public LocationSettingService lss;

    public ArrayNode getFastCas() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Location> fastLoc = lr.findByResType("[\"fast-casual\"]");
        ArrayNode returnList = mapper.createArrayNode();

        //Return everything but ID to Frontend
        for (int i = 0; i < fastLoc.size(); i++) {
            if (lss.getEnabled(fastLoc.get(i).getTitle())) {
                ObjectNode locationNode = mapper.createObjectNode();

                locationNode.put("title", fastLoc.get(i).getTitle());
                locationNode.put("slug", fastLoc.get(i).getSlug());
                locationNode.set("restaurant_type", mapper.readTree(fastLoc.get(i).getResType()));
                locationNode.put("facility", fastLoc.get(i).getFacility());
                locationNode.set("dietary_type", mapper.readTree(fastLoc.get(i).getDietType()));

                returnList.add(locationNode);
            }
        }

        return returnList;
    }
}