package com.example.converters;

import com.example.dto.GenreDto;
import com.example.dto.MovieViewDto;
import com.example.dto.PromoDto;
import com.example.dto.SectionContentDto;
import com.example.models.Genre;
import com.example.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class ContentConverter {
    public static List<PromoDto> convertPromos(List<Movie> movies) {
        List<PromoDto> promos = new ArrayList<>();

        for (Movie movie : movies) {
            PromoDto promo = new PromoDto();
            promo.setId(movie.id);
            promo.setPosterUrl(movie.posterUrl);
            promos.add(promo);
        }

        return promos;
    }

    public static List<SectionContentDto> convertSectionContent(List<Movie> movies) {
        List<SectionContentDto> content = new ArrayList<>();

        for (Movie movie : movies) {
            SectionContentDto sectionContent = new SectionContentDto();
            sectionContent.id = movie.id;
            sectionContent.name = movie.name;
            sectionContent.posterUrl = movie.posterUrl;
            content.add(sectionContent);
        }

        return content;
    }


    public static MovieViewDto convertMovie(Movie movie) {
        MovieViewDto movieViewDto = new MovieViewDto();
        movieViewDto.id = movie.id;
        movieViewDto.name = movie.name;
        movieViewDto.description = movie.description;
        movieViewDto.slogan = movie.slogan;
        movieViewDto.posterUrl = movie.posterUrl;
        movieViewDto.country = movie.country;
        movieViewDto.contentType = movie.contentType;
        movieViewDto.ratings = movie.ratings;
        movieViewDto.trailerInfo = movie.trailerInfo;
        movieViewDto.reviews = movie.reviews;
        movieViewDto.genres = movie.genres;
        movieViewDto.releaseDate = movie.releaseDate;

        return movieViewDto;
    }

    public static List<GenreDto> convertGenres(List<Genre> genres) {
        List<GenreDto> genreDtos = new ArrayList<>();

        for (Genre genre : genres) {
            GenreDto genreDto = new GenreDto();
            genreDto.id = genre.id;
            genreDto.name = genre.name;
            genreDtos.add(genreDto);
        }

        return genreDtos;
    }

    public static List<MovieViewDto> convertMovies(List<Movie> movies) {
        List<MovieViewDto> movieViewDtos = new ArrayList<>();

        for (Movie movie : movies) {
            movieViewDtos.add(convertMovie(movie));
        }

        return movieViewDtos;
    }
}
