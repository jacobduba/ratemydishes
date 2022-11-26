package com.example.backend.review;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "menu_items")
@Component
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cached_rating", columnDefinition = "FLOAT")
    private float rating;

    //will need to create scheduled logic to count number of review objects linked to one menu item
    @Column(name = "cached_num_of_ratings", columnDefinition = "INT")
    private int numRatings;

    @Column(name = "title", columnDefinition = "TINYTEXT")
    private String title;

    //Determined by slug + location ID number from menus obj
    @Column(name = "api_location_id", columnDefinition = "VARCHAR(50)")
    private String locID;

    @Column(name = "menu_id", columnDefinition = "BIGINT")
    private long menuID;

    public MenuItem(float rating, int numRatings, String title, String locID, long menuID) {
        this.rating = rating;
        this.numRatings = numRatings;
        this.title = title;
        this.locID = locID;
        this.menuID = menuID;
    }
}

