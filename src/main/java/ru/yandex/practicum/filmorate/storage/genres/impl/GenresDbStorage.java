package ru.yandex.practicum.filmorate.storage.genres.impl;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Primary;
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
    public void addFilmGenres(Integer filmId, Set<Genre> genres) {
        if (genres != null && !genres.isEmpty()) {
            String sqlString = "MERGE INTO film_genre (film_id, genre_id) VALUES (?, ?)";
            jdbcTemplate.batchUpdate(sqlString, genres, genres.size(),
                    (ps, genre) -> {
                        ps.setInt(1, filmId);
                        ps.setInt(2, genre.getId());
                    });
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

    @Override
    public Map<Integer, LinkedHashSet<Genre>> getAllFilmsGenres() {
        String sqlString = "SELECT f.film_id, g.genre_id, g.name " +
                "FROM film_genre f " +
                "LEFT JOIN genres g ON f.genre_id = g.genre_ID " +
                "ORDER BY f.film_id";
        Map<Integer, LinkedHashSet<Genre>> results = new HashMap<>();
        jdbcTemplate.query(sqlString, (ResultSet rs) -> {
            if (!results.containsKey(rs.getInt("film_id"))) {
                results.put(rs.getInt("film_id"), new LinkedHashSet<Genre>());
            }
            results.get(rs.getInt("film_id")).add(Genre.builder()
                    .id(rs.getInt("genre_id"))
                    .name(rs.getString("name"))
                    .build());
        });
        return results;
    }
}
