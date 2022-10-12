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
        ArrayNode menuNode = mapper.createArrayNode();
        //Query Entire Table
        List menusList = mr.findAll();

        for(int i = 0; i < menusList.size(); i++) {
            //object row represents a row and its field values
            Object row = menusList.get(i);
            Class cl = row.getClass();

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

            //to remove the double quotes
            String slugVal1 = slugVal;
            String slugVal2 = slugVal1.substring(1, slugVal.length() - 1);

            String titleVal1 = titleVal;
            String titleVal2 = titleVal1.substring(1, titleVal.length() - 1);
            //Check to return correct menu
            if (slugVal2.equals(slug)) {
                ObjectNode menuObj = mapper.createObjectNode();

                menuObj.put("slug", slugVal2);
                menuObj.put("title", titleVal2);
                menuObj.put("Menu", menusVal);

                menuNode.add(menuObj);
            }
        }
        return menuNode;
    }
}
