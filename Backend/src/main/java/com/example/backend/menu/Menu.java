package com.example.backend.menu;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Getter
@Setter
@DynamicUpdate
@Entity
@Component
public class Menu {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "menus", columnDefinition = "longblob")
    private String menus;

    @Column(name = "clear_menus", columnDefinition = "longblob")
    private String clearMenus;

    public Menu(String slug, String title, String menus, String clearMenus) {
        this.title = title;
        this.slug = slug;
        this.menus = menus;
        this.clearMenus = clearMenus;
    }

    public Menu() {}
}