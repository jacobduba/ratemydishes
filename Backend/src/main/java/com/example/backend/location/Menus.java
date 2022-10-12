package com.example.backend.location;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Map;

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

    @Column(length = 16777215, name = "menus")
    private String menus;


    public Menus(String title, String menus) {
        this.title = title;
        this.menus = menus;

    }

    public Menus() {

    }

    public Menus(String slug1, String title1, String menu1) {
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

    public void setSlug(String slug) {this.title = title;}
    public void setTitle(String title) {
        this.title = title;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

}
