package ru.yandex.practicum.filmorate.storage.filmLikes;

public interface FilmLikesStorage {
    void addFilmLike(Integer filmId, Integer userId);

    void removeFilmLike(Integer filmId, Integer userId);

    Integer getFilmLikeCount(Integer filmId);
}
