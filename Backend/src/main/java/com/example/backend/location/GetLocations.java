package com.example.backend.location;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
@Service
public class GetLocations {
    @Autowired
    public LocationRepository lr;

    public ArrayNode getHTML(String urlToRead) throws Exception {
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

        ObjectMapper mapper= new ObjectMapper();
        ArrayNode jsonArray = (ArrayNode) mapper.readTree(result.toString());
        return jsonArray;
    }

    /*public void populateTable(JsonArray arr) {  //Parse JSON Array
        for (int i = 0; i < arr.size(); i++) {
            //Need to type cast array element to obj
            JsonObject jsonObj = (JsonObject) arr.get(i);
            //Query table data from each JsonObj
            String title1 = String.valueOf(jsonObj.get("title"));
            String slug1 = String.valueOf(jsonObj.get("slug"));
            String facility1 = String.valueOf(jsonObj.get("facility"));
            String restaurant_type1 = String.valueOf(jsonObj.get("locationType"));
            String dietary_type1 = String.valueOf(jsonObj.get("dietaryType"));

            Locations l = new Locations(title1, slug1, facility1, restaurant_type1, dietary_type1);
            lr.save(l);

        }*/
}


