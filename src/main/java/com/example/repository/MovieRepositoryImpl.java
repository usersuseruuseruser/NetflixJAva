package com.example.repository;


import com.example.dto.FilterDto;
import com.example.models.Genre;
import com.example.models.Movie;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class MovieRepositoryImpl implements MovieRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Movie> getContentByFilter(FilterDto filterDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filterDto.name != null) {
            predicates.add(cb.like(cb.lower(movie.get("name")), "%" + filterDto.name.toLowerCase() + "%"));
        }

        if (filterDto.country != null) {
            predicates.add(cb.equal(movie.get("country"), filterDto.country));
        }

        if (filterDto.genres != null && !filterDto.genres.isEmpty()) {
            CriteriaBuilder subCb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> subQuery = subCb.createQuery(Long.class);
            Root<Genre> subGenre = subQuery.from(Genre.class);
            subQuery.select(subGenre.join("movies").get("id")).where(subGenre.get("id").in(filterDto.genres));
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();
            predicates.add(movie.get("id").in(ids));
        }

        if (filterDto.types != null && !filterDto.types.isEmpty()) {
            CriteriaBuilder subCb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> subQuery = subCb.createQuery(Long.class);
            Root<Movie> subMovie = subQuery.from(Movie.class);
            subQuery.select(subMovie.get("id")).where(subMovie.get("contentType").in(filterDto.types));
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();
            predicates.add(movie.get("id").in(ids));
        }

        if (filterDto.releaseYearFrom != 0 && filterDto.releaseYearTo != 0) {
            predicates.add(cb.between(movie.get("releaseDate"), LocalDate.of(filterDto.releaseYearFrom,1,1)
                    , LocalDate.of(filterDto.releaseYearTo,1,1)));
        }
        if (filterDto.releaseYearFrom != 0) {
            predicates.add(cb.greaterThanOrEqualTo(movie.get("releaseDate"), LocalDate.of(filterDto.releaseYearFrom,1,1)));
        }
        if (filterDto.releaseYearTo != 0) {
            predicates.add(cb.lessThanOrEqualTo(movie.get("releaseDate"), LocalDate.of(filterDto.releaseYearTo,1,1)));
        }

        if (filterDto.ratingFrom != 0 && filterDto.ratingTo != 0) {
            predicates.add(cb.between(movie.get("ratings").get("localRating"), filterDto.ratingFrom, filterDto.ratingTo));
        }
        if (filterDto.ratingFrom != 0) {
            predicates.add(cb.greaterThanOrEqualTo(movie.get("ratings").get("localRating"), filterDto.ratingFrom));
        }
        if (filterDto.ratingTo != 0) {
            predicates.add(cb.lessThanOrEqualTo(movie.get("ratings").get("localRating"), filterDto.ratingTo));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getResultList();
    }
}
