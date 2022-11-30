package com.example.backend.menu;
import com.example.backend.admin.CategorySettingService;
import com.example.backend.review.MenuItem;
import com.example.backend.review.MenuItemRepository;
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
    private JsonNode jsonNode;

    @Autowired
    MenuItemRepository mir;

    @Autowired
    MenuItem mi;

    @Autowired
    CategorySettingService css;

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
                    JsonNode mDid = mDNode.get("id");
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
                        css.getEnabled(catName.textValue());

                        for (int m = 0; m < menuItems.size(); m++) {
                            ArrayNode miArray = mapper.createArrayNode();
                            JsonNode miNode = menuItems.get(m);
                            JsonNode miName = miNode.get("name");
                            JsonNode miHalal = miNode.get("isHalal");
                            JsonNode miVegan = miNode.get("isVegan");
                            JsonNode miCals = miNode.get("totalCal");
                            JsonNode miVeg = miNode.get("isVegetarian");

                            //Add Menuitem to MenuItem Repo
                            //Grab title
                            String title = String.valueOf(miName);
                            //Grab location Id: menuDisplayId + Location Slug
                            //This will help separate identical food items that exist in more than one location
                            String id = mDid + menuList.get(i).getSlug();
                            //Grab Menu ID from Menu in MenuRepo
                            Menu currMenu = mr.findByTitle(title);
                            long menuId = currMenu.getId();
                            MenuItem menuItem = new MenuItem(0,0,title,id,menuId);
                            //Save MenuItem to Repo
                            //Quick Check to prevent duplication
                            if (!mir.existsByTitle(title)) {
                                mir.save(menuItem);
                            }
                            //create json Objects to map json nodes to
                            ObjectNode name = mapper.createObjectNode();
                            ObjectNode halal = mapper.createObjectNode();
                            ObjectNode vegan = mapper.createObjectNode();
                            ObjectNode cals = mapper.createObjectNode();
                            ObjectNode veg = mapper.createObjectNode();

                            //add json-node to object
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

                    //Check where to place mdObject

                    //First check: Is rowArray empty? If so, create new object to populate it
                    if (rowArray.isEmpty()) {
                        ObjectNode sectObj = mapper.createObjectNode();
                        ArrayNode sectArray = mapper.createArrayNode();
                        sectObj.set("title", mDSection);
                        sectArray.add(mdObj);
                        sectObj.set("array", sectArray);
                        rowArray.add(sectObj);
                    }
                    //When row array is not empty
                    else {
                        for (int y = 0; y < rowArray.size();) {
                            JsonNode sectObj1 = rowArray.get(y);
                            JsonNode currArray1 = sectObj1.get("array");
                            //What I am doing here is getting rid of double quotes for comparison between Object title and current menu section
                            String sect = mDSection.textValue();
                            String title = sectObj1.get("title").textValue();
                            //If rowArray has an object, does the object title match the current menu section? If so, add it to this object
                            if (sect.equals(title)) {
                                ArrayNode currArray = (ArrayNode) currArray1;
                                currArray.add(mdObj);
                            //Else create a new object with the new menu section as its title
                            } else {
                                ObjectNode sectObj = mapper.createObjectNode();
                                ArrayNode sectArray = mapper.createArrayNode();
                                sectObj.put("title", title);
                                sectArray.add(mdObj);
                                sectObj.set("array", sectArray);
                                rowArray.add(sectObj);
                            }
                            //Break to avoid infinite loop
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
