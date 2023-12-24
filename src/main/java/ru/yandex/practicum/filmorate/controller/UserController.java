package ru.yandex.practicum.filmorate.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Integer nextId = 1;

    private HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Создан пользователь {}", user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (user.getId() != null && users.containsKey(user.getId())) {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            users.put(user.getId(), user);
            log.info("Обновлен пользователь {}", user);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private Integer getNextId() {
        return nextId++;
    }
}
