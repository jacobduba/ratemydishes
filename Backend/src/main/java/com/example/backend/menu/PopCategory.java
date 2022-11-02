package com.example.backend.menu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
@Component
public class PopCategory {
    @Autowired
    MenuRepository mr;

    @Autowired
    Menu m;

    public ArrayNode popCats() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode catArray = mapper.createArrayNode();
        //Query Entire Table
        ArrayList<Menu> menuList = (ArrayList<Menu>) mr.findAll();

        //For loop to each row, grab menu, parse for categories
        for (int i = 0; i < menuList.size(); i++) {
            //Create master Object
            ArrayNode menuArray = mapper.createArrayNode();
            //Find current menu
            JsonNode row = mapper.readTree(menuList.get(i).getMenus());
            String location = menuList.get(i).getTitle();

            int count3 = 1;
            for (int j = 0; j < row.size(); j++) {
                ObjectNode rowObj = mapper.createObjectNode();
                //Parse for categories
                JsonNode rowNode = row.get(j);
                String titleVal = location;
                JsonNode mDSection = rowNode.get("section");
                JsonNode menuDisplays = rowNode.get("menuDisplays");
                rowObj.put("Location", titleVal);

                int count2 = 1;
                for (int k = 0; k < menuDisplays.size(); k++) {
                    //child object
                    ObjectNode mDObj = mapper.createObjectNode();
                    JsonNode mDNode = menuDisplays.get(k);
                    JsonNode mDName = mDNode.get("name");
                    JsonNode catVals = mDNode.get("categories");
                    //put into child object
                    mDObj.set("Menu-Display-" + count2 + "", mDName);

                    int count1 = 1;
                    for (int l = 0; l < catVals.size(); l++) {
                        ObjectNode catObj = mapper.createObjectNode();
                        JsonNode catNode = catVals.get(l);
                        JsonNode catName = catNode.get("category");
                        JsonNode menuItems = catNode.get("menuItems");
                        //put into child object
                        catObj.set("Category-" + count2 + "", catName);
                        int count = 1;
                        for (int m = 0; m < menuItems.size(); m++) {
                            ObjectNode miObj = mapper.createObjectNode();
                            JsonNode miNode = menuItems.get(m);
                            JsonNode miName = miNode.get("name");
                            //JsonNode miTraits = miNode.get("traits");
                            JsonNode miHalal = miNode.get("isHalal");
                            JsonNode miVegan = miNode.get("isVegan");
                            JsonNode miCals = miNode.get("totalCal");
                            JsonNode miVeg = miNode.get("isVegetarian");

                            miObj.set("Name-" + count + "", miName);
                            //miObj.set("Traits", miTraits);
                            miObj.set("isHalal-" + count + "", miHalal);
                            miObj.set("isVegan-" + count + "", miVegan);
                            miObj.set("isVegetarian-" + count + "", miVeg);
                            miObj.set("Total-Calories-" + count + "", miCals);

                            //Set function overrides the previous, so using count variable to change name of object
                            catObj.set("Menu-Item-" + count + "", miObj);
                            count++;
                        }
                        mDObj.set("Categories-" + count1 + "", catObj);
                        count1++;
                    }
                    rowObj.set("Section-" + count3 + "", mDSection);
                    rowObj.set("Menu-Display-" + count2 + "", mDObj);
                    count2++;
                }
                menuArray.add(rowObj);
                //Stringify catArray
                String stringObj = menuArray.toString();
                //grab current title
                String title = menuList.get(i).getTitle();
                //grab current row
                Menu currRow = mr.findByTitle(title);
                //save catArray string to currRow
                currRow.setClearMenus(stringObj);
                mr.save(currRow);
            }
            catArray.add(menuArray);
        }
        return catArray;
    }
}