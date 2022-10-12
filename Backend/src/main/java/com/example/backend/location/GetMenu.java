package com.example.backend.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class GetMenu {
@Autowired
MenuRepository mr;

    public ArrayNode returnMenu(String slug) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        //Query Entire Table
        List menusList = mr.findAll();
        ArrayNode menuNode = null;

        for(int i = 0; i < menusList.size(); i++) {
            //object row represents a row and its field values
            Object row = menusList.get(i);
            Class cl = menusList.getClass();

            //Fields of each object
            Field slugField = cl.getDeclaredField("slug");
            Field titleField = cl.getDeclaredField("title");
            Field menusField = cl.getDeclaredField("menus");

            //Setting private fields to accessible
            slugField.setAccessible(true);
            titleField.setAccessible(true);
            menusField.setAccessible(true);

            //Values of Each field
            String slugVal = (String) slugField.get(row);
            String titleVal = (String) titleField.get(row);
            String menusVal = (String) menusField.get(row);


            //Check to return correct menu
            if (slugVal == slug) {
                ObjectNode menuObj = mapper.createObjectNode();

                menuObj.put("slug", slugVal);
                menuObj.put("title", titleVal);
                menuObj.put("Menu", menusVal);

                menuNode.add(menuObj);
            }
        }
        return menuNode;
    }
}
