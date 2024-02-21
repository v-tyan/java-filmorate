package ru.yandex.practicum.filmorate.storage.user.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int nextId = 1;
    private HashMap<Integer, User> users = new HashMap<>();

    private int getNextId() {
        return nextId++;
    }

    @Override
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<Integer>());
        }

        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Создан пользователь {}", user);

        return user;
    }

    @Override
    public User updatUser(User user) {
        if (user.getId() != null && users.containsKey(user.getId())) {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            user.setFriends(users.get(user.getId()).getFriends());
            users.put(user.getId(), user);
            log.info("Обновлен пользователь {}", user);

            return user;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public User getUser(Integer id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void deleteUser(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }
}
