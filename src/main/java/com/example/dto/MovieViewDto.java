package com.example.dto;

import com.example.models.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.Set;

public class MovieViewDto {
    public Long id;

    public String name;
    public String description;
    public String slogan;
    public String posterUrl;
    public String country;
    public ContentType contentType;

    public Ratings ratings;

    public TrailerInfo trailerInfo;

    @JsonManagedReference
    public Set<Review> reviews;

    public Set<Genre> genres;

    public LocalDate releaseDate;
}
