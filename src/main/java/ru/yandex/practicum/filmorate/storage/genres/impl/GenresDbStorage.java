package ru.yandex.practicum.filmorate.storage.genres.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.exceptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;
import ru.yandex.practicum.filmorate.storage.util.mapper.Mapper;

@Primary
@Repository
@RequiredArgsConstructor
public class GenresDbStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        String sqlString = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlString, Mapper::genreMapper);
    }

    @Override
    public Genre getGenre(Integer id) {
        String sqlString = "SELECT * FROM genres WHERE genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlString, Mapper::genreMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException("Genre with id=" + id + " not found");
        }
    }

    @Override
    public void addFilmGenre(Integer filmId, Integer genreId) {
        String sqlString = "MERGE INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sqlString, filmId, genreId);
        } catch (DataAccessException e) {
            throw new GenreNotFoundException("Genre not found");
        }
    }

    @Override
    public List<Genre> getFilmGenres(Integer filmId) {
        String sqlString = "SELECT * FROM genres " +
                "WHERE genre_id IN (SELECT genre_id FROM film_genre WHERE film_id = ?)";
        return jdbcTemplate.query(sqlString, Mapper::genreMapper, filmId);
    }

    @Override
    public void deleteFilmGenres(Integer filmId) {
        String sqlString = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(sqlString, filmId);
    }
}
