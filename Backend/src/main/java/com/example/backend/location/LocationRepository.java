package com.example.backend.location;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface LocationRepository extends JpaRepository<Locations, Long> {
    Locations findById(long id);

    @Transactional
    Locations deleteById(long i);
}
