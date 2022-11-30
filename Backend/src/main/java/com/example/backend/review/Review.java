package com.example.backend.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "rating", columnDefinition = "SMALLINT")
    private long rating;

    @Column(name = "comment", columnDefinition = "TINYTEXT")
    private String comment;

    // TODO one to many relationships with users and Menu items

    public Review(long rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}

