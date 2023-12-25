package ru.yandex.practicum.filmorate.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Update;
import ru.yandex.practicum.filmorate.model.exceptions.FilmNotFoundException;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int nextId = 1;

    private HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Validated(Update.class) @RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Создан фильм {}", film);

        return film;
    }

    @PutMapping
    public Film updatefilm(@Validated(Update.class) @RequestBody Film film) {
        if (film.getId() != null && films.containsKey(film.getId())) {
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
}
