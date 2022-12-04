package com.example.backend.review;

import com.example.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@EnableScheduling
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndMenuItem(User user, MenuItem menuItem);
    ArrayList<Review> findAllByMenuItem(MenuItem menuItem);
}

