package com.example.backend.admin;

import com.example.backend.admin.exceptions.UserNotPrivilegedException;
import com.example.backend.user.User;
import com.example.backend.user.UserService;
import com.example.backend.user.payload.AuthRequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/admin")
public class AdminController {
    private ObjectMapper mapper = new ObjectMapper();
    private LocationSettingService lss;
    private CategorySettingRepository csr;
    private UserService us;

    @Autowired
    public AdminController(LocationSettingService lss, CategorySettingRepository csr, UserService us) {
        this.lss = lss;
        this.csr = csr;
        this.us = us;
    }

    @PostMapping("/get-settings")
    public ObjectNode getSettings(@RequestBody AuthRequestPayload payload) {
        User user = us.getUserFromAuthPayload(payload);

        if (!user.hasRole("admin")) throw new UserNotPrivilegedException();

        ObjectNode returnNode = mapper.createObjectNode();

        ArrayNode locationNode = mapper.createArrayNode();
        for (LocationSetting ls : lss.findAll()) {
            ObjectNode location = mapper.createObjectNode();

            location.put("name", ls.getName());
            location.put("enabled", ls.isEnabled());

            locationNode.add(location);
        }
        returnNode.set("locations", locationNode);

        ArrayNode categoryNode = mapper.createArrayNode();
        for (CategorySetting cs : csr.findAll()) {
            ObjectNode category = mapper.createObjectNode();

            category.put("title", cs.getName());
            category.put("enabled", cs.isEnabled());

            categoryNode.add(category);
        }
        returnNode.set("categories", categoryNode);

        return returnNode;
    }
}
