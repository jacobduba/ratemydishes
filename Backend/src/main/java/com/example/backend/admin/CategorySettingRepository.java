package com.example.backend.admin;

import com.example.backend.admin.payload.CategorySettingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySettingRepository extends JpaRepository<CategorySetting, Long> {
    List<CategorySetting> findAll();
    List<CategorySettingResponse> findAllProjectedBy();
    CategorySetting findByName(String name);
}
