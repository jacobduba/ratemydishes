package com.example.backend.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class LocationControllerTest {

    @Autowired
    private LocationController LocationController;

    @Test
    void getAllRestaurants() {
        assertNotNull(LocationController);
    }
}