package com.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    @JsonIgnore
    public Review review;

    @Column(name = "review_id")
    public long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public User user;

    @Column(name = "user_id")
    public long userId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id", referencedColumnName = "id")
//    @JsonManagedReference
//
//    public CommentNotification commentNotification;

    public String text;

    public LocalDateTime writtenAt;
}
