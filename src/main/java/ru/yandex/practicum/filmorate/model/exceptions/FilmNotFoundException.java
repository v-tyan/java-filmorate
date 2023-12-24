package ru.yandex.practicum.filmorate.model.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String message) {
        super(message);
    }
}
