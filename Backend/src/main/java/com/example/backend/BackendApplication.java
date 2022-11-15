package com.example.backend;

import com.example.backend.role.Role;
import com.example.backend.role.RoleService;
import com.example.backend.user.User;
import com.example.backend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableScheduling
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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.backend"))
                .build();
    }
}
