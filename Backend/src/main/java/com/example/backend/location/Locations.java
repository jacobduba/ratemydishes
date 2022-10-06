package com.example.backend.location;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Locations {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "restaurant_type")
    private String res_type;

    @Column(name = "slug")
    private String slug;

    @Column(name = "facility")
    private String facility;

    @Column(name = "dietary_type")
    private String diet_type;

    public Locations(String title, String res_type, String slug, String facility, String diet_type) {
        this.title = title;
        this.res_type = res_type;
        this.slug = slug;
        this.facility = facility;
        this.diet_type = diet_type;
    }

    public Locations() {

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

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String facility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getDiet_type() {
        return diet_type;
    }

    public void setDiet_type(String diet_type) {
        this.diet_type = diet_type;
    }
}
