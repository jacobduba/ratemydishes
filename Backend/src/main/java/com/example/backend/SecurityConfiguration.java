package com.example.backend;

import com.example.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class SecurityConfiguration {
    private UserRepository userRepo;

    // Constructor injection is preferred over field injection.
    @Autowired
    public SecurityConfiguration(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public AuthenticationManager filterChain(AuthenticationManagerBuilder auth) throws Exception {
        return 
    }
}
