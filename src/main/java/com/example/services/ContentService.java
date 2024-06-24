package com.example.services;

import com.example.converters.ContentConverter;
import com.example.dto.*;
import com.example.enums.ContentTypes;
import com.example.models.ContentType;
import com.example.models.Genre;
import com.example.models.Movie;
import com.example.repository.ContentTypeRepository;
import com.example.repository.GenreRepository;
import com.example.repository.MovieRepository;
import com.example.repository.MovieRepositoryCustom;
import com.example.services.Abstractions.IContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService implements IContentService {
    private MovieRepository movieRepository;
    private ContentTypeRepository contentTypeRepository;
    private MovieRepositoryCustom movieRepositoryCustom;
    private GenreRepository genreRepository;
    private static final int PROMOS_COUNT = 5;
    private static final int SECTION_COUNT = 20;


    public ContentService(MovieRepository movieRepository, ContentTypeRepository contentTypeRepository
    , MovieRepositoryCustom movieRepositoryCustom, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.contentTypeRepository = contentTypeRepository;
        this.movieRepositoryCustom = movieRepositoryCustom;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<PromoDto> getPromos() {

        return ContentConverter.convertPromos(movieRepository.findRandomMovies(PROMOS_COUNT));
    }

    @Override
    public List<SectionDto> getSections() {
        List<SectionDto> sections = new ArrayList<>();
        List<Movie> movies = movieRepository.findRandomMovies(SECTION_COUNT);
        SectionDto section = new SectionDto();
        section.name = ContentTypes.MOVIE.toString();

        section.contents = ContentConverter.convertSectionContent(movies);

        sections.add(section);
        return sections;
    }

    @Override
    public MovieViewDto getMovieById(long id) {
        Movie movie = movieRepository.findMovieById(id);
        return ContentConverter.convertMovie(movie);
    }

    @Override
    public List<ContentType> getContentTypes() {
        return contentTypeRepository.findAll();
    }

    @Override
    public List<GenreDto> getGenres() {
        List<Genre> genres = genreRepository.findAll();

        return ContentConverter.convertGenres(genres);
    }

    @Override
    public List<MovieViewDto> getContentByFilter(FilterDto filterDto) {
        List<Movie> movie = movieRepositoryCustom.getContentByFilter(filterDto);

        for (Movie movie1 : movie) {
            movie1.reviews = null;
        }

        return ContentConverter.convertMovies(movie);
    }
}
