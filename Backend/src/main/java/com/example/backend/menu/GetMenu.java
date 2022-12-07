package com.example.backend.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GetMenu {
    @Autowired
    MenuRepository mr;

    public ObjectNode returnMenu(String slug) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode menuNode = mapper.createObjectNode();
        //Query Entire Table
        ArrayList<Menu> menusList = mr.findBySlug(slug);


        //For loop to return menu
        for (int i = 0; i < menusList.size(); i++) {
            JsonNode menusObj = mapper.readTree(menusList.get(i).getClearMenus());

            menuNode.put("title", menusList.get(i).getTitle());
            menuNode.put("slug", menusList.get(i).getSlug());
            menuNode.set("menu", menusObj);
        }
        return menuNode;
    }
}
