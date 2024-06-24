package com.example.services.Abstractions;

import com.example.dto.*;
import com.example.models.ContentType;
import com.example.models.Genre;
import com.example.models.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContentService {
    List<PromoDto> getPromos();
    List<SectionDto> getSections();

    MovieViewDto getMovieById(long id);

    List<ContentType> getContentTypes();

    List<GenreDto> getGenres();

    List<MovieViewDto> getContentByFilter(FilterDto filterDto);
}
