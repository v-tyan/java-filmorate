package ru.yandex.practicum.filmorate.storage.filmLikes.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.storage.filmLikes.FilmLikesStorage;

@Primary
@Repository
@RequiredArgsConstructor
public class FilmLikesDbStorage implements FilmLikesStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFilmLike(Integer filmId, Integer userId) {
        String sqlString = "MERGE INTO films_likes (film_id, user_id) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sqlString, filmId, userId);
        } catch (DataAccessException e) {
            throw new FilmNotFoundException("Film not found");
        }
    }

    @Override
    public void removeFilmLike(Integer filmId, Integer userId) {
        String sqlString = "DELETE FROM films_likes WHERE film_id = ? AND user_id = ?";
        int resultUpdate = jdbcTemplate.update(sqlString, filmId, userId);
        if (resultUpdate == 0) {
            throw new FilmNotFoundException("Film not found");
        }
    }

    @Override
    public Integer getFilmLikeCount(Integer filmId) {
        String sqlString = "SELECT COUNT(*) FROM films_likes WHERE film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlString, Integer.class, filmId);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException("Film not found");
        }
    }

}
