package ru.yandex.practicum.filmorate.model.exceptions;

public class MpaNotFoundException extends RuntimeException {
    public MpaNotFoundException(String message) {
        super(message);
    }
}
