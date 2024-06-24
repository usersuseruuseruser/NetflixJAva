package com.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String description;

    public float price;

    @ManyToMany
    @JsonBackReference
    public Set<Movie> movies;
}
