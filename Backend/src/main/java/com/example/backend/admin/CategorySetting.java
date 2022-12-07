package com.example.backend.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "category_settings")
@Getter
@Setter
@NoArgsConstructor
public class CategorySetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean enabled;

    public CategorySetting(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}
