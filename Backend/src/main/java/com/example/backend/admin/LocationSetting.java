package com.example.backend.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "location_settings")
@Getter @Setter @NoArgsConstructor
public class LocationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean enabled;

    public LocationSetting(String title, boolean enabled) {
        this.title = title;
        this.enabled = enabled;
    }
}
