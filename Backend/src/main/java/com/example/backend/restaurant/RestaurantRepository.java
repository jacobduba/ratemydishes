package com.example.backend.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findById(long id);

    @Transactional
    Restaurant deleteById(long i);
}
