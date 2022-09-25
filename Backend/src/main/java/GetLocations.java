import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    public void populateTable(JsonArray arr) {  //Stopped here, need to parse JSON Array
        for (int i = 0; i < arr.size(); i++) {
            JsonObject jsonobj = arr.getAsJsonObject(i);
            String name = String.valueOf(jsonobj.get("name"));
            String url = jsonelement.getAsString("url");
        }
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(getHTML("https://dining.iastate.edu/wp-json/dining/menu-hours/get-locations/"));
    }
}