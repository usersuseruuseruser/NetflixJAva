package com.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name="reviews")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonBackReference
    public User user;

    @Column(name = "user_id")
    public long userId;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    @JsonBackReference
    public Movie movie;

    @Column(name = "movie_id")
    public long movieId;

    public String text;

    public boolean isPositive;

    public int score;

    public OffsetDateTime writtenAt;

    @OneToMany
    @JoinColumn(name="review_id")
    public Set<Comment> comments;

}
