package com.example.backend.location;

import com.example.backend.menu.MenuRepository;
import com.example.backend.menu.Menu;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.*;

@Service
public class GetSingleLocation {
    @Autowired
    public MenuRepository mr;
    @Autowired
    public LocationRepository lr;

    public ArrayNode getHTML(String urlToRead, String slug, Long timestamp) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
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
            ArrayNode response = (ArrayNode) mapper.readTree(String.valueOf(result));

            return response;
        }
    }

    @Modifying
    public void populateTable(ArrayNode arr) {  //Parse JSON Array
        for (int i = 0; i < arr.size(); i++) {
            //Need to type cast array element to node
            JsonNode jsonNode = arr.get(i);
            //Query table data from each JsonObj
            String menu1 = jsonNode.get("menus").toString();
            String title1 = jsonNode.get("title").textValue();
            String slug1 = jsonNode.get("slug").textValue();

            //Check to prevent duplicates before input
            try {
                Menu oldM = mr.findByTitle(title1);
                Menu m = new Menu(slug1, title1, menu1, null);
                Location l = lr.findBySlug(slug1);
                m.setLocation(l);
                l.setMenu(m);
                if (!mr.existsByTitle(title1)) {
                    mr.save(m);
                    lr.save(l);
                } else if (mr.existsByTitle(title1)) {
                    mr.delete(oldM);
                    lr.save(l);
                    mr.save(m);
                }
            } catch (Exception e) {
                // TODO (Jacob) running into exceptions when running update on local
                // This is my hacked ass solution to get it working... everything seems to working
            }
        }
    }
}