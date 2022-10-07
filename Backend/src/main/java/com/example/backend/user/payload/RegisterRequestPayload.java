package com.example.backend.user.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class RegisterRequestPayload {
    @Pattern(regexp = "^[a-z0-9]{3,8}$") // net ids are 3 to 8 lowercase letters and numbers
    private String netId;
    @Min(5)
    private String password;

//    private String first_name;
//    private String last_name;

    public String getNetId() {
        return netId;
    }

    public String getPassword() {
        return password;
    }

//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }

    public String toString() {
        return "RegisterRequestPayload[netId: " + netId
                + ", password: " + password
//                + ", first_name: " + first_name
//                + ", last_name: " + last_name
                + "]";
    }
}
