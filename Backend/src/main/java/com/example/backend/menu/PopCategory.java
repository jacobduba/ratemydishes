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
import java.util.Objects;


@Service
@Component
public class PopCategory {
    @Autowired
    MenuRepository mr;

    @Autowired
    Menu m;
    private JsonNode jsonNode;

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
                    //Create object in section array that holds Lunch array. Lunch array holds lunch Mds
                    //load menu display object into lunch array
                    //sectArray.add(mdObj);
                    //check where to place
                    if (rowArray.isEmpty()) {
                        ObjectNode sectObj = mapper.createObjectNode();
                        ArrayNode sectArray = mapper.createArrayNode();
                        sectObj.set("title", mDSection);
                        sectArray.add(mdObj);
                        sectObj.set("array", sectArray);
                        rowArray.add(sectObj);
                    }
                    else {
                        for (int y = 0; y < rowArray.size();y++) {
                            JsonNode sectObj1 = rowArray.get(y);
                            JsonNode currArray1 = sectObj1.get("array");
                            String dq = String.valueOf(mDSection);
                            String resDq = dq.substring(1, dq.length() - 1);
                            String test = sectObj1.get("title").toString();
                            String test1 = test.substring(1, test.length() - 1);
                            if (test1.equals(resDq)) {
                                ArrayNode currArray = (ArrayNode) currArray1;
                                currArray.add(mdObj);
                            } else {
                                ObjectNode sectObj = mapper.createObjectNode();
                                ArrayNode sectArray = mapper.createArrayNode();
                                sectObj.put("title", resDq);
                                sectArray.add(mdObj);
                                sectObj.set("array", sectArray);
                                rowArray.add(sectObj);
                            }
                            break;
                        }
                    }
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
        //Parent Array is what is stored in the menu repository
        return parentArray;
    }
}
