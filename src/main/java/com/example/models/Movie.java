package com.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, length = 128)
    public String name;
    @Column(length = 1568)
    public String description;
    public String slogan;
    public String posterUrl;
    public String country;
    public LocalDate releaseDate;

    @ManyToOne
    public ContentType contentType;

    @OneToOne
    public Ratings ratings;

    @OneToOne
    public TrailerInfo trailerInfo;

    @OneToMany()
    public Set<Review> reviews;

    @ManyToMany
    @JsonManagedReference
    public Set<Subscription> allowedSubscriptions;

    @ManyToMany
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genres_id"))
    public Set<Genre> genres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    public TrailerInfo getTrailerInfo() {
        return trailerInfo;
    }

    public void setTrailerInfo(TrailerInfo trailerInfo) {
        this.trailerInfo = trailerInfo;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Subscription> getAllowedSubscriptions() {
        return allowedSubscriptions;
    }

    public void setAllowedSubscriptions(Set<Subscription> allowedSubscriptions) {
        this.allowedSubscriptions = allowedSubscriptions;
    }
}
