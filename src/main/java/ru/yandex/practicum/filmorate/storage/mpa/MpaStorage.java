package ru.yandex.practicum.filmorate.storage.mpa;

import java.util.List;

import ru.yandex.practicum.filmorate.model.MPA;

public interface MpaStorage {
    List<MPA> getMPAs();

    MPA getMPA(Integer id);
}
