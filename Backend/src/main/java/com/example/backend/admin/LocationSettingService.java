package com.example.backend.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationSettingService {
    private LocationSettingRepository locationSettingRepository;

    @Autowired
    public LocationSettingService(LocationSettingRepository locationSettingRepository) {
        this.locationSettingRepository = locationSettingRepository;
    }

    public boolean getEnabled(String title) {
        LocationSetting locationSetting = locationSettingRepository.findByTitle(title);

        if (locationSetting == null) {
            createNewLocationSetting(title);
            return true;
        }

        return locationSetting.isEnabled();
    }

    private void createNewLocationSetting(String title) {
        LocationSetting locationSetting = new LocationSetting(title, true);
        locationSettingRepository.save(locationSetting);
    }
}
