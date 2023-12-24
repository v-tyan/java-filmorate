package ru.yandex.practicum.filmorate.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Film.
 */
@Data
public class Film {
    private Integer id;

    @NotEmpty(message = "название не может быть пустым")
    private String name;

    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    // Duration in minutes
    @Positive
    private int duration;
}
