package com.example.backend.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CategorySettingRepository extends JpaRepository<CategorySetting, Long> {
    ArrayList<CategorySetting> findAll();
    CategorySetting findByName(String name);
}
