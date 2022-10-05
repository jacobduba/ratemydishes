package com.example.backend;

import com.example.backend.restaurant.Restaurant;
import com.example.backend.restaurant.RestaurantRepository;
import com.example.backend.user.User;
import com.example.backend.user.UserController;
import com.example.backend.user.UserRepository;
import com.example.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    CommandLineRunner init(UserService us) {
        return args -> {
            us.createNewUser("jduba", "test");
        };
    }
}
