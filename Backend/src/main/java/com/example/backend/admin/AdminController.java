package com.example.backend.admin;

import com.example.backend.admin.exceptions.UserNotPrivilegedException;
import com.example.backend.admin.payload.SettingsResponse;
import com.example.backend.admin.payload.ToggleSettingRequest;
import com.example.backend.admin.payload.ToggleSettingResponse;
import com.example.backend.user.User;
import com.example.backend.user.UserService;
import com.example.backend.user.payload.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/admin")
public class AdminController {
    private final LocationSettingService lss;
    private final CategorySettingService css;
    private final UserService us;

    @Autowired
    public AdminController(LocationSettingService lss, CategorySettingService css, UserService us) {
        this.lss = lss;
        this.css = css;
        this.us = us;
    }

    @Operation(summary = "(Jacob) Given valid admin token, return location settings and category settings.")
    @PostMapping("/get-settings")
    public SettingsResponse getSettings(@RequestBody AuthRequest payload) {
        User user = us.getUserFromAuthPayload(payload);

        if (!user.hasRole("admin")) throw new UserNotPrivilegedException();

        return new SettingsResponse(lss.getResponses(), css.getResponses());
    }

    @Operation(summary = "(Jacob) Given location name and valid admin token, set location on or off.")
    @PostMapping("toggle-location")
    public ToggleSettingResponse toggleLocation(@RequestBody ToggleSettingRequest payload) {
        User user = us.getUserFromAuthPayload(payload);
        if (!user.hasRole("admin")) throw new UserNotPrivilegedException();

        lss.setEnabled(payload.getName(), payload.isEnabled());

        return new ToggleSettingResponse(payload.getName(), payload.isEnabled());
    }

    @Operation(summary = "(Jacob) Given category name and valid admin token, set category on or off.")
    @PostMapping("toggle-category")
    public ToggleSettingResponse toggleCategory(@RequestBody ToggleSettingRequest payload) {
        User user = us.getUserFromAuthPayload(payload);
        if (!user.hasRole("admin")) throw new UserNotPrivilegedException();

        css.setEnabled(payload.getName(), payload.isEnabled());

        return new ToggleSettingResponse(payload.getName(), payload.isEnabled());
    }
}
