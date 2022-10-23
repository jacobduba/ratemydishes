package com.example.backend;

import com.example.backend.role.Role;
import com.example.backend.role.RoleService;
import com.example.backend.user.User;
import com.example.backend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService, RoleService roleService) {
       return args -> {
           Role adminRole = roleService.createNewRole("admin");
           User adminUser = userService.createNewUser("admin", "admin");
           roleService.giveRole(adminUser, adminRole);
        };
    }
}
