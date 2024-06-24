package com.example.models;


@javax.persistence.Entity
@javax.persistence.Table(name = "reviews_likes")
public class ReviewsLikes {
    @javax.persistence.Id
    @javax.persistence.Column(name = "id")
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private long id;

    @javax.persistence.Column(name = "review_id")
    private long reviewId;

    @javax.persistence.Column(name = "user_id")
    private long userId;


    public ReviewsLikes() {
    }

    public ReviewsLikes(long reviewId, long userId) {
        this.reviewId = reviewId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
