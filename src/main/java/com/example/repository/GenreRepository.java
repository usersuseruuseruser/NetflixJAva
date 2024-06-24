package com.example.repository;

import com.example.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
