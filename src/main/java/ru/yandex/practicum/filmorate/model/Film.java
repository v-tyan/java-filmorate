package ru.yandex.practicum.filmorate.model;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.constraints.FilmReleaseDateConstraint;

/**
 * Film.
 */
@Data
@Builder
public class Film {
    private Integer id;

    @NotEmpty(groups = { Update.class }, message = "название не может быть пустым")
    private String name;

    @Size(groups = { Update.class }, max = 200, message = "максимальная длина описания — 200 символов")
    private String description;

    @FilmReleaseDateConstraint(groups = { Update.class })
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    // Duration in minutes
    @Positive(groups = { Update.class }, message = "продолжительность фильма должна быть положительной")
    private int duration;

    private Set<Integer> userLikes;
}
