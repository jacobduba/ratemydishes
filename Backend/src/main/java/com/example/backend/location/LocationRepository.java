package com.example.backend.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LocationRepository extends JpaRepository<Locations, Long> {
    Locations findById(long id);
    Locations findByTitle(String title);

    @Transactional
    Locations deleteById(long i);
}
