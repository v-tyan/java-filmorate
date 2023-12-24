package ru.yandex.practicum.filmorate.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmNotFoundException;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private Integer nextId = 1;

    private static final Date MIN_RELEASE_DATE = new Date(-2335564800000L);

    private HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().before(MIN_RELEASE_DATE)) {
            throw new ValidationException();
        }

        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Создан фильм {}", film);

        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> updatefilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().before(MIN_RELEASE_DATE)) {
            throw new ValidationException();
        }

        if (film.getId() != null && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Обновлен фильм {}", film);

            return new ResponseEntity<>(film, HttpStatus.OK);
        } else {
            throw new FilmNotFoundException("film not found");
        }
    }

    private Integer getNextId() {
        return nextId++;
    }
}
