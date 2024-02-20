package ru.yandex.practicum.filmorate.storage.genres;

import java.util.List;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenresStorage {
    List<Genre> getGenres();

    Genre getGenre(Integer id);

    void addFilmGenre(Integer filmId, Integer genreId);

    List<Genre> getFilmGenres(Integer filmId);

    void deleteFilmGenres(Integer filmId);
}
