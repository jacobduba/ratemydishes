package com.example.backend.admin;

import com.example.backend.admin.exceptions.LocationDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationSettingService {
    private LocationSettingRepository locationSettingRepository;

    @Autowired
    public LocationSettingService(LocationSettingRepository locationSettingRepository) {
        this.locationSettingRepository = locationSettingRepository;
    }

    public List<LocationSetting> findAll() {
        return locationSettingRepository.findAll();
    }

    public boolean getEnabled(String name) {
        LocationSetting locationSetting = locationSettingRepository.findByName(name);

        if (locationSetting == null) {
            createNewLocationSetting(name);
            return true;
        }

        return locationSetting.isEnabled();
    }

    private void createNewLocationSetting(String name) {
        LocationSetting locationSetting = new LocationSetting(name, true);
        locationSettingRepository.save(locationSetting);
    }

    public void setEnabled(String name, boolean enabled) {
        LocationSetting locationSetting = locationSettingRepository.findByName(name);

        if (locationSetting == null) throw new LocationDoesNotExistException();

        locationSetting.setEnabled(enabled);
        locationSettingRepository.save(locationSetting);
    }
}
