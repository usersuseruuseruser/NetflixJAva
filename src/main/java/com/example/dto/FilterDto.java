package com.example.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class FilterDto {
    public String name;
    public List<Integer> types;
    public List<Integer> genres;
    public String country;
    public int releaseYearFrom;
    public int releaseYearTo;
    public double ratingFrom;
    public double ratingTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getReleaseYearFrom() {
        return releaseYearFrom;
    }

    public void setReleaseYearFrom(int releaseYearFrom) {
        this.releaseYearFrom = releaseYearFrom;
    }

    public int getReleaseYearTo() {
        return releaseYearTo;
    }

    public void setReleaseYearTo(int releaseYearTo) {
        this.releaseYearTo = releaseYearTo;
    }

    public double getRatingFrom() {
        return ratingFrom;
    }

    public void setRatingFrom(double ratingFrom) {
        this.ratingFrom = ratingFrom;
    }

    public double getRatingTo() {
        return ratingTo;
    }

    public void setRatingTo(double ratingTo) {
        this.ratingTo = ratingTo;
    }
}
