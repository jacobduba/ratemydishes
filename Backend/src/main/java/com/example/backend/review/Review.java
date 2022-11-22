package com.example.backend.review;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Getter
@Setter
@DynamicUpdate
@Entity
@Component
@Data
public class Review {

        @javax.persistence.Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long reviewID;

        @Column(name = "rating", columnDefinition = "SMALLINT")
        private long rating;

        @Column(name = "comment", columnDefinition = "TINYTEXT")
        private String comment;

        @Column(name = "menuItemID", columnDefinition = "BIGINT")
        private long menuID;

        @Column(name = "userID", columnDefinition = "BIGINT")
        private long userID;

       /* @OneToOne
        private LocationSetting locationSetting;

        @OneToOne
        private Review review;*/

        public Review(long rating, String comment, long menuID, long userID) {
            this.rating = rating;
            this.comment = comment;
            this.menuID = menuID;
            this.userID = userID;
        }

        public Review() {}
    }

