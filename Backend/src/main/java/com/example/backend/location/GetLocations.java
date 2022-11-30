package com.example.backend.location;

import com.example.backend.admin.LocationSettingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
@Configuration
@EnableScheduling
public class GetLocations {
    @Autowired
    public LocationRepository lr;

    @Autowired
    public LocationSettingService lss;

    public void getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode jsonArray = (ArrayNode) mapper.readTree(result.toString());
        //Deleting current vals in repo to replace with new
        lr.deleteAll();
        for (int i = 0; i < jsonArray.size(); i++) {
            //Need to type cast array element to obj
            ObjectNode jsonObj = (ObjectNode) jsonArray.get(i);
            //Query table data from each JsonObj
            String title1 = jsonObj.get("title").textValue();
            String slug1 = jsonObj.get("slug").textValue();
            String facility1 = jsonObj.get("facility").textValue();
            String restaurant_type1 = jsonObj.get("locationType").toString();
            String dietary_type1 = jsonObj.get("dietaryType").toString();

            //Check to prevent duplicates before input
            Location l = new Location(dietary_type1, facility1, restaurant_type1, slug1, title1);
            if (lr.existsByTitle(title1) == false) {
                lr.save(l);
                lss.getEnabled(title1); // Create new LocationSetting if it doesn't exist.
            }
        }
    }
}


