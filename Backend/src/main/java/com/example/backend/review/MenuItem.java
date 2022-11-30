package com.example.backend.review;

import com.example.backend.menu.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "menu_items")
@Component
@Getter @Setter @NoArgsConstructor
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
    // Why did we need this?
    // We need this to identify menu items that may exist in more than one, or we could just do that with Menu_id?
    //@Column(name = "api_location_id", columnDefinition = "VARCHAR(50)")
    //private String apiLocationId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    // TODO One to many relationship between menu, reivews

    public MenuItem(String title, Menu menu) {
        this.title = title;
        this.menu = menu;
    }
}

