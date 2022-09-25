package com.example.backend.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {
    @Autowired
    RestaurantRepository rr;

    @GetMapping("/restaurants")
    List<Restaurant> getAllRestaurants() {
        return rr.findAll();
    }
}

