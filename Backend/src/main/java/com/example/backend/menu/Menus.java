package com.example.backend.menu;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;
import javax.persistence.*;
@DynamicUpdate
@Entity
@Component
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

    @Column(name = "clear_menus", columnDefinition = "json")
    private String clearMenus;

    public Menus(String slug, String title, String menus, String clearMenus) {
        this.title = title;
        this.slug = slug;
        this.menus = menus;
        this.clearMenus = clearMenus;
    }

    public Menus() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {return slug; }
    public String getTitle() {
        return title;
    }
    public String getClearMenus() { return clearMenus;}

    public void setSlug(String slug) {this.slug = slug;}
    public void setTitle(String title) {
        this.title = title;
    }
    public void setClearMenus(String clearMenus) {
        this.clearMenus = clearMenus;
    }


    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }
}
