package com.authApi.psoftg2.libraryapi.bookManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.authApi.psoftg2.libraryapi.bookManagement.model.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("SELECT g FROM Genre g WHERE g.id = :id")
    Optional<Genre> findGenreById(Long id);
}
