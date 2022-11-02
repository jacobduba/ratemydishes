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
        ArrayNode parentArray = mapper.createArrayNode();
        //Query Entire Table
        ArrayList<Menu> menuList = (ArrayList<Menu>) mr.findAll();

        //For loop to each row, grab menu, parse for categories
        for (int i = 0; i < menuList.size(); i++) {
            //Create master Array
            ArrayNode menuArray = mapper.createArrayNode();
            //Find current menu
            JsonNode row = mapper.readTree(menuList.get(i).getMenus());

            for (int j = 0; j < row.size(); j++) {
                ArrayNode rowArray = mapper.createArrayNode();
                //Parse for categories
                JsonNode rowNode = row.get(j);
                JsonNode mDSection = rowNode.get("section");
                JsonNode menuDisplays = rowNode.get("menuDisplays");

                for (int k = 0; k < menuDisplays.size(); k++) {
                    //child object
                    ArrayNode mDArray = mapper.createArrayNode();
                    JsonNode mDNode = menuDisplays.get(k);
                    JsonNode mDName = mDNode.get("name");
                    JsonNode catVals = mDNode.get("categories");
                    //put into child object
                    mDArray.add(mDName);

                    for (int l = 0; l < catVals.size(); l++) {
                        ArrayNode catArray = mapper.createArrayNode();
                        JsonNode catNode = catVals.get(l);
                        JsonNode catName = catNode.get("category");
                        JsonNode menuItems = catNode.get("menuItems");
                        //put into child object
                        catArray.add(catName);
                        for (int m = 0; m < menuItems.size(); m++) {
                            ArrayNode miArray = mapper.createArrayNode();
                            JsonNode miNode = menuItems.get(m);
                            JsonNode miName = miNode.get("name");
                            JsonNode miHalal = miNode.get("isHalal");
                            JsonNode miVegan = miNode.get("isVegan");
                            JsonNode miCals = miNode.get("totalCal");
                            JsonNode miVeg = miNode.get("isVegetarian");

                            //create json Objects to map json nodes to
                            ObjectNode name = mapper.createObjectNode();
                            ObjectNode halal = mapper.createObjectNode();
                            ObjectNode vegan = mapper.createObjectNode();
                            ObjectNode cals = mapper.createObjectNode();
                            ObjectNode veg = mapper.createObjectNode();

                            //add jsonnode to object
                            name.set("name", miName);
                            halal.set("isHalal", miHalal);
                            vegan.set("isVegan", miVegan);
                            cals.set("total-calories", miCals);
                            veg.set("isVegetarian", miVeg);

                            //add object to parent array
                            miArray.add(name);
                            miArray.add(halal);
                            miArray.add(vegan);
                            miArray.add(cals);
                            miArray.add(veg);

                            //Set function overrides the previous, so using count variable to change name of object
                            ObjectNode miObj = mapper.createObjectNode();
                            miObj.set("Food-Item", miArray);
                            catArray.add(miObj);
                        }
                        ObjectNode catObj = mapper.createObjectNode();
                        catObj.set("Food-Type", catArray);
                        mDArray.add(catObj);
                    }
                    ObjectNode mdObj = mapper.createObjectNode();
                    mdObj.set("Menu-Display", mDArray);
                    rowArray.add(mDSection);
                    rowArray.add(mdObj);
                }
                ObjectNode rowObj = mapper.createObjectNode();
                rowObj.set("Section", rowArray);
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
            parentArray.add(menuArray);
        }
        return parentArray;
    }
}
