package com.example.backend.location;


import com.example.backend.location.LocationRepository;
import com.example.backend.location.Locations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetSingleLocation {
    @Autowired
    public MenuRepository mr;

    public ArrayNode getHTML(String slug, Long timestamp, String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        //Updating base url to accommodate time and slug params
        URL url = new URL(urlToRead + "?slug=" + slug + "&time=" + timestamp);
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
        return jsonArray;
    }

    public void populateTable(ArrayNode arr) throws IOException {  //Parse JSON Array
        for (int i = 0; i < arr.size(); i++) {
            //Need to type cast array element to obj
            ObjectNode jsonObj = (ObjectNode) arr.get(i);
            //Query table data from each JsonObj
            String menu1 = String.valueOf(jsonObj.get("menus"));
            String title1 = String.valueOf(jsonObj.get("title"));
            String slug1 = String.valueOf(jsonObj.get("slug"));


            //Check to prevent duplicates before input
            Menus m = new Menus(slug1, title1, menu1);
            if (mr.existsByTitle(title1) == false)
                mr.save(m);

        }
    }
}