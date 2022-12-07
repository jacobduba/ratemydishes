package com.example.backend.admin;

import com.example.backend.admin.payload.AdminSettingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LocationSettingRepository extends JpaRepository<LocationSetting, Long> {
    ArrayList<LocationSetting> findAll();

    List<AdminSettingResponse> findAllProjectedBy();

    LocationSetting findByName(String name);
}
