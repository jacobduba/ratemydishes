package com.example.backend.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "menu_items")
@Component
@Getter
@Setter
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

    // Location slug
    @Column
    private String slug;

//    Because Menu is deleted, it's not a smart idea to link it one-to-one
//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

    public MenuItem(String title, String slug) {
        this.title = title;
        this.slug = slug;
    }
}

