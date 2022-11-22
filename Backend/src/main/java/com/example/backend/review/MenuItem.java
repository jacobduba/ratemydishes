package com.example.backend.review;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class MenuItem {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuItemID;

    @Column(name = "cached_rating", columnDefinition = "FLOAT")
    private float rating;

    //will need to create scheduled logic to count number of review objects linked to one menu item
    @Column(name = "cached_num_of_ratings", columnDefinition = "INT")
    private int numRatings;

    @Column(name = "title", columnDefinition = "TINYTEXT")
    private String title;

    //Determined by slug + location ID number from menus obj
    @Column(name = "api_location_id", columnDefinition = "VARCHAR")
    private String locID;

    @Column(name = "menuID", columnDefinition = "BIGINT")
    private long menuID;

       /* @OneToOne
        private LocationSetting locationSetting;

        @OneToOne
        private Review review;*/

    public MenuItem(float rating, int numRatings, String title, String locID, long menuID) {
        this.rating = rating;
        this.numRatings = numRatings;
        this.title = title;
        this.locID = locID;
        this.menuID = menuID;
    }

    public MenuItem() {}
}

