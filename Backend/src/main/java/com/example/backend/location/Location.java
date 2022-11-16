package com.example.backend.location;

import com.example.backend.admin.LocationSetting;
import com.example.backend.menu.Menu;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Data
public class Location {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "restaurant_type")
    private String resType;

    @Column(name = "slug")
    private String slug;

    @Column(name = "facility")
    private String facility;

    @Column(name = "dietary_type")
    private String dietType;

    @OneToOne
    private LocationSetting locationSetting;

    @OneToOne
    private Menu menu;

    public Location(String dietType, String facility, String resType, String slug, String title) {
        this.title = title;
        this.resType = resType;
        this.slug = slug;
        this.facility = facility;
        this.dietType = dietType;
    }

    public Location() {}
}
