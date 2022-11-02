package com.example.backend.admin.payload;

import com.example.backend.user.payload.AuthRequestPayload;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ToggleLocationRequestPayload extends AuthRequestPayload {
    private String name;
    private boolean enabled;
}
