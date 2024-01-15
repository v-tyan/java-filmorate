package ru.yandex.practicum.filmorate.service.film;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage inMemoryFilmStorage;

    public Film addLike(Integer filmId, Integer userId) {
        Film interimFilm = inMemoryFilmStorage.getFilm(filmId);
        interimFilm.getUserLikes().add(userId);
        return interimFilm;
    }

    public Film deleLike(Integer filmId, Integer userId) {
        Film interimFilm = inMemoryFilmStorage.getFilm(filmId);
        if (!interimFilm.getUserLikes().contains(userId)) {
            throw new UserNotFoundException("User Like not found");
        }
        interimFilm.getUserLikes().remove(userId);
        return interimFilm;
    }

    public List<Film> getPopular(Integer count) {
        return inMemoryFilmStorage.getFilms().stream()
        .sorted((f1, f2) -> f2.getUserLikes().size() - f1.getUserLikes().size())
        .limit(count)
        .collect(Collectors.toList());
    }
}
