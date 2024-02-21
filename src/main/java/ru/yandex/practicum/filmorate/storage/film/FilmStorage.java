package ru.yandex.practicum.filmorate.storage.film;

import java.util.List;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    public List<Film> getFilms();

    public Film getFilm(Integer id);

    public Film createFilm(Film film);

    public Film updatefilm(Film film);

    public List<Film> getPopular(int count);
}