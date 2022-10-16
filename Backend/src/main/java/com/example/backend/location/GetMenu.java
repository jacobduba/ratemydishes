package com.example.backend.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetMenu {
@Autowired
MenuRepository mr;

    public ObjectNode returnMenu(String slug) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode menuNode = mapper.createObjectNode();
        //Query Entire Table
        ArrayList<Menus> menusList = mr.findBySlug("\"" + slug + "\"");


        //For loop to return menu
        for(int i=0;i < menusList.size(); i++) {
            JsonNode menusObj = mapper.readTree(menusList.get(i).getMenus());

            menuNode.put("Slug", menusList.get(i).getSlug());
            menuNode.put("Title", menusList.get(i).getTitle());
            menuNode.set("Menu", menusObj);
        }
        return menuNode;
    }
}
