package ru.yandex.practicum.filmorate.storage.user;

import java.util.List;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    public List<User> getUsers();

    public User getUser(Integer id);

    public User createUser(User user);

    public User updatUser(User user);

    public void deleteUser(Integer id);
}