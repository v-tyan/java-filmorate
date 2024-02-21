package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

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
    private LocalDate releaseDate;

    // Duration in minutes
    @Positive(groups = { Update.class }, message = "продолжительность фильма должна быть положительной")
    private int duration;

    private Integer userLikes;

    private LinkedHashSet<Genre> genres;

    private MPA mpa;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("FILM_NAME", name);
        values.put("DESCRIPTION", description);
        values.put("RELEASE_DATE", releaseDate);
        values.put("DURATION", duration);
        values.put("MPA_ID", mpa.getId());
        return values;
    }
}
