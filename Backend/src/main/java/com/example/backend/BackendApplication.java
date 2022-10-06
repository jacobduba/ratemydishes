package com.example.backend;

import com.example.backend.location.GetLocations;
import com.example.backend.location.LocationRepository;
import com.example.backend.location.Locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;


@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

   /* CommandLineRunner initLocation(LocationRepository lr) {
        return args ->

    }*/
}
