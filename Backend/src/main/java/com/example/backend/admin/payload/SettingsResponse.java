package com.example.backend.admin.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class SettingsResponse {
    private List<AdminSettingResponse> locations;
    private List<AdminSettingResponse> categories;
}

