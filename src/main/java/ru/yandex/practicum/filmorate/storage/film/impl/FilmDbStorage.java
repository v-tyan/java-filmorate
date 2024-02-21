package ru.yandex.practicum.filmorate.storage.film.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.util.mapper.Mapper;

@Primary
@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> getFilms() {
        String sqlString = "SELECT films.*, m.* " +
                "FROM films " +
                "JOIN mpa m ON m.MPA_ID = films.mpa_id";
        return jdbcTemplate.query(sqlString, Mapper::filmMapper);
    }

    @Override
    public Film getFilm(Integer id) {
        String sqlString = "SELECT FILMS.*, M.* " +
                "FROM FILMS " +
                "JOIN MPA M ON M.MPA_ID = FILMS.MPA_ID " +
                "WHERE FILMS.FILM_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlString, Mapper::filmMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException("Film with id=" + id + " not found");
        }
    }

    @Override
    public Film createFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        int id = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        film.setId(id);
        return film;
    }

    @Override
    public Film updatefilm(Film film) {
        String sqlString = "UPDATE films SET film_name = ?, description = ?, release_date = ?, " +
                "duration = ?, mpa_id = ?" +
                "WHERE FILM_ID = ?";
        int resultUpdate = jdbcTemplate.update(sqlString,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        if (resultUpdate == 0) {
            throw new FilmNotFoundException("Film" + film + "not found");
        }
        return film;
    }

    @Override
    public List<Film> getPopular(int count) {
        String sqlString = "SELECT films.*, m.* "
                +
                "FROM films " +
                "LEFT JOIN films_likes fl ON films.FILM_ID = fl.film_id " +
                "LEFT JOIN mpa m on m.MPA_ID = films.mpa_id " +
                "GROUP BY films.FILM_ID, fl.film_id IN ( " +
                "SELECT film_id " +
                "FROM films_likes " +
                ") " +
                "ORDER BY COUNT(fl.film_id) DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sqlString, Mapper::filmMapper, count);
    }

}
