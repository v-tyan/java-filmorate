package ru.yandex.practicum.filmorate.storage.genres;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenresStorage {
    List<Genre> getGenres();

    Genre getGenre(Integer id);

    void addFilmGenres(Integer filmId, Set<Genre> genres);

    List<Genre> getFilmGenres(Integer filmId);

    void deleteFilmGenres(Integer filmId);

    Map<Integer, LinkedHashSet<Genre>> getAllFilmsGenres();
}
