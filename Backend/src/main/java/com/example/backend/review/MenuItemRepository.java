package com.example.backend.review;

import com.example.backend.review.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@EnableScheduling
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    MenuItem findById(long id);

    boolean existsByTitle(String title);

    ArrayList<MenuItem> findBySlug(String slug);
    MenuItem findByTitle(String title);
    @Transactional
    MenuItem deleteById(long i);
}


