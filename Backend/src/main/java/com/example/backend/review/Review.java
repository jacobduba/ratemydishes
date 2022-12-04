package com.example.backend.review;

import com.example.backend.user.User;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem mi;

    public Review(long rating, String comment, User user, MenuItem mi) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.mi = mi;
    }
}

