package ru.yandex.practicum.filmorate.model.constraints;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilmReleaseDateConstraintValidator implements ConstraintValidator<FilmReleaseDateConstraint, Date> {
    // 28 декабря 1895 года - минимальная дата релиза фильма
    private static final Date MIN_RELEASE_DATE = new Date(-2335564800000L);

    @Override
    public boolean isValid(Date filmDate, ConstraintValidatorContext context) {
        return filmDate.after(MIN_RELEASE_DATE);
    }

}
