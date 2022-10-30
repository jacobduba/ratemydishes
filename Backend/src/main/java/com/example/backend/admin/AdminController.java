package com.example.backend.admin;

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
    private UserService us;

    @Autowired
    public AdminController(LocationSettingService lss, UserService us) {
        this.lss = lss;
        this.us = us;
    }

    @PostMapping("/get-settings")
    public ObjectNode getSettings(@RequestBody AuthRequestPayload payload) {
        User user = us.getUserFromAuthPayload(payload);

        ObjectNode returnNode = mapper.createObjectNode();

        ArrayNode locationNode = mapper.createArrayNode();
        for (LocationSetting ls : lss.findAll()) {
            ObjectNode location = mapper.createObjectNode();

            location.put("title", ls.getTitle());
            location.put("enabled", ls.isEnabled());

            locationNode.add(location);
        }
        returnNode.set("locationNode", locationNode);

        return returnNode;
    }
}
