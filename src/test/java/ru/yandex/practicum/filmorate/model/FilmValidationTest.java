package ru.yandex.practicum.filmorate.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FilmValidationTest {
    @Autowired
    private Validator validator;

    @Test
    public void shouldBeValidFilm() {
        Film validFilm = Film.builder()
                .id(0)
                .name("validFilm")
                .description("description")
                .releaseDate(LocalDate.now())
                .duration(130)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(validFilm, Update.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldBeInvalidFilmName() {
        Film invalidFilm = Film.builder()
                .id(0)
                .name("")
                .description("description")
                .releaseDate(LocalDate.now())
                .duration(130)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(invalidFilm, Update.class);
        assertTrue(!violations.isEmpty());
        assertTrue(violations.size() == 1);
        assertTrue(violations.iterator().next().getMessage().equals("название не может быть пустым"));
    }

    @Test
    public void shouldBeInvalidFilmDescription() {
        Film invalidFilm = Film.builder()
                .id(0)
                .name("valid")
                .description("description".repeat(100))
                .releaseDate(LocalDate.now())
                .duration(130)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(invalidFilm, Update.class);
        assertTrue(!violations.isEmpty());
        assertTrue(violations.size() == 1);
        assertTrue(violations.iterator().next().getMessage().equals("максимальная длина описания — 200 символов"));
    }

    @Test
    public void shouldBeInvalidFilmReleaseDate() {
        Film invalidFilm = Film.builder()
                .id(0)
                .name("valid")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(130)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(invalidFilm, Update.class);
        assertTrue(!violations.isEmpty());
        assertTrue(violations.size() == 1);
        assertTrue(violations.iterator().next().getMessage().equals("дата релиза — не раньше 28 декабря 1895 года"));
    }

    @Test
    public void shouldBeInvalidFilmDuration() {
        Film invalidFilm = Film.builder()
                .id(0)
                .name("valid")
                .description("description")
                .releaseDate(LocalDate.now())
                .duration(-130)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(invalidFilm, Update.class);
        assertTrue(!violations.isEmpty());
        assertTrue(violations.size() == 1);
        assertTrue(
                violations.iterator().next().getMessage().equals("продолжительность фильма должна быть положительной"));
    }
}
