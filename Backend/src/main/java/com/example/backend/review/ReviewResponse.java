package com.example.backend.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    String comment;
    long rating;
    String netId;

    public ReviewResponse(Review review) {
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.netId = review.getUser().getNetId();
    }
}
