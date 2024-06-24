package com.example.repository;

import com.example.models.Genre;
import com.example.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
    @Query(nativeQuery = true, value = "SELECT * FROM movies ORDER BY RANDOM() LIMIT :limit")
    List<Movie> findRandomMovies(int limit);

    @Query(nativeQuery = true, value = "SELECT * FROM movies WHERE id = :id")
    Movie findMovieById(long id);

    @Query(nativeQuery = true, value = "select * from movies")
    List<Movie> findAllByGenreId(String genre);
}
