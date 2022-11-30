package com.example.backend.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

@Repository
@EnableScheduling
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    boolean existsByTitle(String title);
}


