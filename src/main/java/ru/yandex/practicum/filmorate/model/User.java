package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;

    @Email(groups = { Update.class }, message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;

    @Pattern(groups = { Update.class }, regexp = "^\\S+$", message = "логин не может быть пустым и содержать пробелы")
    private String login;

    private String name;

    @NotNull(groups = { Update.class }, message = "дата рождения не может быть пустой")
    @Past(groups = { Update.class }, message = "дата рождения не может быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Set<Integer> friends;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }
}
