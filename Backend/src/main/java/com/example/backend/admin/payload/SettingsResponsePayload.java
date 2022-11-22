package com.example.backend.admin.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class SettingsResponsePayload {
    private List<LocationSettingResponse> locations;
    private List<CategorySettingResponse> categories;
}

