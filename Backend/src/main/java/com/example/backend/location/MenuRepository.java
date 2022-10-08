package com.example.backend.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MenuRepository extends JpaRepository<Menus, Long> {
    Menus findById(long id);
    boolean existsByTitle(String title);

    @Transactional
    Menus deleteById(long i);
}
