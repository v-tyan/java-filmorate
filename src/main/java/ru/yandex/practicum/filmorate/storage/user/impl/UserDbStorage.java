package ru.yandex.practicum.filmorate.storage.user.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.util.mapper.Mapper;

@Primary
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getUsers() {
        String sqlString = "SELECT * FROM users";
        return jdbcTemplate.query(sqlString, Mapper::userMapper);
    }

    @Override
    public User getUser(Integer id) {
        String sqlString = "SELECT * FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlString, Mapper::userMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User with id=" + id + " not found");
        }
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        int id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public User updatUser(User user) {
        String sqlString = "UPDATE users SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? " +
                "WHERE USER_ID = ?";
        int updateResult = jdbcTemplate.update(sqlString,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        if (updateResult == 0) {
            throw new UserNotFoundException("User" + user + "not found");
        }
        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        String sqlString = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sqlString, id);
    }

}
