package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenresStorage genresStorage;

    @GetMapping
    public List<Genre> getFilms() {
        return genresStorage.getGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable int id) {
        return genresStorage.getGenre(id);
    }
}
