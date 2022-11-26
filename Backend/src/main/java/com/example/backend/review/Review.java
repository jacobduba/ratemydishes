package com.example.backend.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "rating", columnDefinition = "SMALLINT")
    private long rating;

    @Column(name = "comment", columnDefinition = "TINYTEXT")
    private String comment;

    @Column(name = "menu_item_id", columnDefinition = "BIGINT")
    private long menuID;

    @Column(name = "user_id", columnDefinition = "BIGINT")
    private long userID;

    public Review(long rating, String comment, long menuID, long userID) {
        this.rating = rating;
        this.comment = comment;
        this.menuID = menuID;
        this.userID = userID;
    }
}

