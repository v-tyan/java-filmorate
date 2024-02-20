package ru.yandex.practicum.filmorate.storage.util.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.experimental.UtilityClass;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

@UtilityClass
public class Mapper {
    public User userMapper(ResultSet resultSet, int row) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    public Film filmMapper(ResultSet resultSet, int row) throws SQLException {
        MPA mpa = MPA.builder()
                .id(resultSet.getInt("mpa.mpa_id"))
                .name(resultSet.getString("mpa.name"))
                .build();
        return Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(mpa)
                .build();
    }

    public Genre genreMapper(ResultSet resultSet, int row) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name"))
                .build();
    }

    public MPA mpaMapper(ResultSet resultSet, int row) throws SQLException {
        return MPA.builder()
                .id(resultSet.getInt("mpa_id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
