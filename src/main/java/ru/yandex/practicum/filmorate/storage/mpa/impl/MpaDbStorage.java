package ru.yandex.practicum.filmorate.storage.mpa.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.exceptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.util.mapper.Mapper;

@Primary
@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MPA> getMPAs() {
        String sqlString = "SELECT * FROM mpa";
        return jdbcTemplate.query(sqlString, Mapper::mpaMapper);
    }

    @Override
    public MPA getMPA(Integer id) {
        String sqlString = "SELECT * FROM mpa WHERE mpa_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlString, Mapper::mpaMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException("Mpa with id=" + id + " not found");
        }
    }

}
