package com.example.repository;

import com.example.dto.FilterDto;
import com.example.models.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepositoryCustom {
    List<Movie> getContentByFilter(FilterDto filterDto);
}
