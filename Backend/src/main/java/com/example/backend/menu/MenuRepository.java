package com.example.backend.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@EnableScheduling
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findById(long id);

    boolean existsByTitle(String title);

    ArrayList<Menu> findBySlug(String slug);

    Menu findByTitle(String title);

    @Transactional
    Menu deleteById(long i);
    Menu deleteByTitle(String title);
}
