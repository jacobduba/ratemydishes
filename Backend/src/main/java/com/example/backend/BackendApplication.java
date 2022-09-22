package com.example.backend;

import com.example.backend.restaurant.Restaurant;
import com.example.backend.restaurant.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initRestaurant(RestaurantRepository rr) {
        return args -> {
            Restaurant r = new Restaurant("Windows");
            rr.save(r);
        };
    }
}
