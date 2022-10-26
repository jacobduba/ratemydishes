package com.example.backend.location;

import lombok.Data;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Entity
@Component
@Data
public class Menus {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "menus", columnDefinition = "json")
    private String menus;

    public Menus(String slug, String title, String menus) {
        this.title = title;
        this.slug = slug;
        this.menus = menus;
    }

    public Menus() {}
}
