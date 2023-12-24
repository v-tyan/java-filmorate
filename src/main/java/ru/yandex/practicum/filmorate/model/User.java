package ru.yandex.practicum.filmorate.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class User {
    private Integer id;

    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @NotEmpty(message = "логин не может быть пустым")
    @Pattern(regexp = "^\\S+$", message = "логин не может содержать пробелы")
    private String login;
    private String name;

    @NotNull(message = "дата рождения не может быть пустым")
    @Past(message = "дата рождения не может быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}
