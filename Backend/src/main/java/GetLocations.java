import com.google.gson.*;
import java.sql.Connection;

import java.io.*;
import java.net.*;


public class GetLocations {

    public static JsonArray getHTML(String urlToRead) throws Exception {
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

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(result.toString());
        return jsonArray;
    }
    public static void populateTable(JsonArray arr) {  //Parse JSON Array
        for (int i = 0; i < arr.size(); i++) {
            //Need to type cast array element to obj
            JsonObject jsonObj = (JsonObject) arr.get(i);
            //Query table data from each JsonObj
            String title1 = String.valueOf(jsonObj.get("title"));
            String slug1 = String.valueOf(jsonObj.get("slug"));
            String facility1 = String.valueOf(jsonObj.get("facility"));
            String restaurant_type1 = String.valueOf(jsonObj.get("locationType"));
            String dietary_type1 = String.valueOf(jsonObj.get("dietaryType"));


            INSERT INTO rmd_db.Locations (title, restaurant_type, slug, facility, dietary_type)
            VALUES (null, null, null, null, null);

        }
    }

    public static void main(String[] args) throws Exception
    {
        Connection conn = .getConnection();

        populateTable(getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/"));
    }
}