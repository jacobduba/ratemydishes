package com.example.backend.admin.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ToggleSettingResponse {
    private String name;
    private boolean isEnabled;
}
