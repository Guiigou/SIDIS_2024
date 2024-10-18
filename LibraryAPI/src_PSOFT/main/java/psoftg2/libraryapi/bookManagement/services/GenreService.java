package psoftg2.libraryapi.bookManagement.services;

import psoftg2.libraryapi.bookManagement.model.Genre;

import java.util.Optional;

public interface GenreService {
    Optional<Genre> getGenreById(final Long id);
    Iterable<Genre> getGenres();
}
