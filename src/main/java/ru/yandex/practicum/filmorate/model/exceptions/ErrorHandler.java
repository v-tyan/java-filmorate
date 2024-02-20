package ru.yandex.practicum.filmorate.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(final UserNotFoundException e) {
        log.warn("Ошибка UserNotFoundException - {}", e.getMessage());
        return new ErrorResponse("UserNotFound", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFound(final FilmNotFoundException e) {
        log.warn("Ошибка FilmNotFoundException - {}", e.getMessage());
        return new ErrorResponse("FilmNotFound", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenreNotFound(final GenreNotFoundException e) {
        log.warn("Ошибка GenreNotFoundException - {}", e.getMessage());
        return new ErrorResponse("GenreNotFound", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMpaNotFound(final MpaNotFoundException e) {
        log.warn("Ошибка MpaNotFoundException - {}", e.getMessage());
        return new ErrorResponse("MpaNotFound", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error("Ошибка InternalServerError - {}", e.getMessage());
        return new ErrorResponse("InternalServerError", e.getMessage());
    }
}
