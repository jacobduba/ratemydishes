package com.example.backend.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findById(long id);
    Location findByTitle(String title);
    Location findBySlug(String slug);
    boolean existsByTitle(String title);
    ArrayList<Location> findByResType(String resType);
    @Transactional
    Location deleteById(long i);
}
