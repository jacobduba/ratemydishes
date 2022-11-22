package com.example.backend.admin.payload;

import com.example.backend.user.payload.AuthRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ToggleSettingRequest extends AuthRequest {
    private String name;
    private boolean enabled;
}
