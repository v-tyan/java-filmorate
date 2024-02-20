package ru.yandex.practicum.filmorate.model.constraints;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilmReleaseDateConstraintValidator implements ConstraintValidator<FilmReleaseDateConstraint, LocalDate> {
    // 28 декабря 1895 года - минимальная дата релиза фильма
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate filmDate, ConstraintValidatorContext context) {
        return filmDate.isAfter(MIN_RELEASE_DATE);
    }

}
