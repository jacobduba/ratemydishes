package com.example.backend.admin;

import com.example.backend.admin.exceptions.CategoryDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySettingService {
    private CategorySettingRepository categorySettingRepository;

    @Autowired
    public CategorySettingService(CategorySettingRepository csr) {
        this.categorySettingRepository = csr;
    }

    public List<CategorySetting> findAll() {
        return categorySettingRepository.findAll();
    }

    public boolean getEnabled(String name) {
        CategorySetting locationSetting = categorySettingRepository.findByName(name);

        if (locationSetting == null) {
            createNewCategorySetting(name);
            return true;
        }

        return locationSetting.isEnabled();
    }

    private void createNewCategorySetting(String name) {
        CategorySetting categorySetting = new CategorySetting(name, true);
        categorySettingRepository.save(categorySetting);
    }

    public void setEnabled(String name, boolean enabled) {
        CategorySetting categorySetting = categorySettingRepository.findByName(name);

        if (categorySetting == null) throw new CategoryDoesNotExistException();

        categorySetting.setEnabled(enabled);
        categorySettingRepository.save(categorySetting);
    }
}
