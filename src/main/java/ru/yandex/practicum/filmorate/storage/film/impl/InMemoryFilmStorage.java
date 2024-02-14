package ru.yandex.practicum.filmorate.storage.film.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private int nextId = 1;
    private HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        if (film.getUserLikes() == null) {
            film.setUserLikes(new HashSet<Integer>());
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Создан фильм {}", film);

        return film;
    }

    @Override
    public Film updatefilm(Film film) {
        if (film.getId() != null && films.containsKey(film.getId())) {
            film.setUserLikes(films.get(film.getId()).getUserLikes());
            films.put(film.getId(), film);
            log.info("Обновлен фильм {}", film);

            return film;
        } else {
            throw new FilmNotFoundException("film not found");
        }
    }

    private int getNextId() {
        return nextId++;
    }

    @Override
    public Film getFilm(Integer id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new FilmNotFoundException("film not found");
        }
    }
}
