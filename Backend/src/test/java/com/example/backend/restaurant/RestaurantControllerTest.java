package com.example.backend.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantControllerTest {

    @Autowired
    private RestaurantController restaurantController;

    @Test
    void getAllRestaurants() {
        assertNotNull(restaurantController);
    }
}