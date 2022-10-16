package com.example.backend.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface LocationRepository extends JpaRepository<Locations, Long> {
    Locations findById(long id);
    boolean existsByTitle(String title);

    ArrayList<Locations> findByResType(String resType);
    @Transactional
    Locations deleteById(long i);
}
