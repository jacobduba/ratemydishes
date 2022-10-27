package com.example.backend.menu;

import org.springframework.stereotype.Component;
import javax.persistence.*;

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

    @Column(name = "categories", columnDefinition = "json")
    private String categories;

    public Menus(String slug, String title, String menus, String categories) {
        this.title = title;
        this.slug = slug;
        this.menus = menus;
        this.categories = categories;
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
    public String getCategories() { return categories;}

    public void setSlug(String slug) {this.slug = slug;}
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }


    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }
}
