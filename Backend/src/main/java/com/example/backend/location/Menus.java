package com.example.backend.location;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Component
public class Menus {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "menus")
    private ArrayNode menus;


    public Menus(String title, ArrayNode menus) {
        this.title = title;
        this.menus = menus;

    }

    public Menus() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayNode getMenus() {
        return menus;
    }

    public void setMenus(ArrayNode menus) {
        this.menus = menus;
    }

}
