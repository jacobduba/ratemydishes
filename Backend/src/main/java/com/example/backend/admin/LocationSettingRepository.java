package com.example.backend.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LocationSettingRepository extends JpaRepository<LocationSetting, Long> {
    ArrayList<LocationSetting> findAll();
    LocationSetting findByTitle(String title);
}
