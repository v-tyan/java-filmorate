package ru.yandex.practicum.filmorate.storage.friendship.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.util.mapper.Mapper;

@Primary
@Repository
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getFriend(Integer id) {
        String sqlString = "SELECT USERS.USER_ID, email, login, name, birthday " +
                "FROM USERS " +
                "LEFT JOIN friendship f on users.USER_ID = f.friend_id " +
                "where f.user_id = ?";
        return jdbcTemplate.query(sqlString, Mapper::userMapper, id);
    }

    @Override
    public List<User> getCommon(Integer id, Integer friendId) {
        String sqlString = "SELECT u.USER_ID, email, login, name, birthday " +
                "FROM friendship AS f " +
                "LEFT JOIN users u ON u.USER_ID = f.friend_id " +
                "WHERE f.user_id = ? AND f.friend_id IN ( " +
                "SELECT friend_id " +
                "FROM friendship AS f " +
                "LEFT JOIN users AS u ON u.USER_ID = f.friend_id " +
                "WHERE f.user_id = ? )";

        return jdbcTemplate.query(sqlString, Mapper::userMapper, id, friendId);
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        String sqlString = "DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlString, id, friendId);
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        String sqlString = "MERGE INTO FRIENDSHIP (USER_ID, FRIEND_ID) " +
                "VALUES (?, ?)";
        try {
            jdbcTemplate.update(sqlString, id, friendId);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("User not found");
        }
    }

}
