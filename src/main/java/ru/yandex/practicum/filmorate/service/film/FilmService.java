package ru.yandex.practicum.filmorate.service.film;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.filmLikes.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final GenresStorage genresStorage;
    private final FilmLikesStorage filmLikesStorage;

    public Film addLike(Integer filmId, Integer userId) {
        filmLikesStorage.addFilmLike(filmId, userId);
        log.info("UserID {} added like to filmID {}", userId, filmId);
        return filmStorage.getFilm(filmId);
    }

    public Film deleLike(Integer filmId, Integer userId) {
        filmLikesStorage.removeFilmLike(filmId, userId);
        log.info("UserID {} removed like from filmID {}", userId, filmId);
        return filmStorage.getFilm(filmId);
    }

    public List<Film> getPopular(Integer count) {
        List<Film> films = filmStorage.getPopular(count);
        for (Film film : films) {
            film.setGenres(new LinkedHashSet<Genre>(genresStorage.getFilmGenres(film.getId())));
        }
        return films;
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilm(id);
        film.setGenres(new LinkedHashSet<Genre>(genresStorage.getFilmGenres(film.getId())));
        return film;
    }

    public Film createFilm(Film film) {
        Film interimFilm = filmStorage.createFilm(film);
        genresStorage.addFilmGenres(interimFilm.getId(), film.getGenres());
        interimFilm.setGenres(film.getGenres());
        return interimFilm;
    }

    public List<Film> getFilms() {
        List<Film> films = filmStorage.getFilms();
        Map<Integer, LinkedHashSet<Genre>> allFilmsGenres = genresStorage.getAllFilmsGenres();
        System.out.println(allFilmsGenres);
        for (Film film : films) {
            if (!allFilmsGenres.containsKey(film.getId())) {
                film.setGenres(new LinkedHashSet<Genre>());
            } else {
                film.setGenres(allFilmsGenres.get(film.getId()));
            }
        }
        return films;
    }

    public Film updatefilm(Film film) {
        genresStorage.deleteFilmGenres(film.getId());
        genresStorage.addFilmGenres(film.getId(), film.getGenres());
        filmStorage.updatefilm(film);
        return film;
    }
}
