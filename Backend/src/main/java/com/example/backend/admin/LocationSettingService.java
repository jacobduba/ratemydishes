package com.example.backend.admin;

import com.example.backend.admin.exceptions.LocationDoesNotExistException;
import com.example.backend.admin.payload.AdminSettingResponse;
import com.example.backend.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationSettingService {
    private final LocationSettingRepository locationSettingRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public LocationSettingService(LocationSettingRepository locationSettingRepository, LocationRepository locationRepository) {
        this.locationSettingRepository = locationSettingRepository;
        this.locationRepository = locationRepository;
    }

    public List<AdminSettingResponse> getResponses() {
        return locationSettingRepository.findAllProjectedBy();
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
