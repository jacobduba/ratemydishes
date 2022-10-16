package com.example.backend.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@EnableScheduling
public interface MenuRepository extends JpaRepository<Menus, Long> {
    Menus findById(long id);

    boolean existsByTitle(String title);

    ArrayList<Menus> findBySlug(String slug);
    @Transactional
    Menus deleteById(long i);
}
