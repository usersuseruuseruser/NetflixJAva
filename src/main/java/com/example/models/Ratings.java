package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ratings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public float kinopoiskRating;
    public float imdbRating;
    public float localRating;

    public float getKinopoiskRating() {
        return kinopoiskRating;
    }

    public void setKinopoiskRating(float kinopoiskRating) {
        this.kinopoiskRating = kinopoiskRating;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float i) {
        this.imdbRating = i;
    }

    public float getLocalRating() {
        return localRating;
    }

    public void setLocalRating(float localRating) {
        this.localRating = localRating;
    }
}
