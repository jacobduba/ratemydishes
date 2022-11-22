package com.example.backend.review;

import com.example.backend.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@EnableScheduling
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findById(long id);

    boolean existsByTitle(String title);

    ArrayList<Review> findBySlug(String slug);
    Review findByTitle(String title);
    @Transactional
    Review deleteById(long i);
    }

