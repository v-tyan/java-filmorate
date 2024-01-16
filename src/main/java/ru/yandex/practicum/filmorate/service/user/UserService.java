package ru.yandex.practicum.filmorate.service.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage inMemoryUserStorage;

    public User addFriend(Integer id, Integer friendId) {
        User interimUser = inMemoryUserStorage.getUser(id);
        inMemoryUserStorage.getUser(friendId).getFriends().add(id);
        interimUser.getFriends().add(friendId);
        log.info("Users {} and {} are now friends", id, friendId);
        return interimUser;
    }

    public User deleteFriend(Integer id, Integer friendId) {
        User interimUser = inMemoryUserStorage.getUser(id);
        if (!interimUser.getFriends().contains(friendId)) {
            throw new UserNotFoundException("User not found");
        }
        interimUser.getFriends().remove(friendId);
        inMemoryUserStorage.getUser(friendId).getFriends().remove(id);
        log.info("Users {} and {} are no longer friends", id, friendId);
        return interimUser;
    }

    public List<User> getFriends(Integer id) {
        log.info("Requested friend list of User {}", id);
        return inMemoryUserStorage.getUser(id).getFriends().stream()
                .map(friend -> inMemoryUserStorage.getUser(friend))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        Set<Integer> common = new HashSet<>(inMemoryUserStorage.getUser(id).getFriends());
        common.retainAll(inMemoryUserStorage.getUser(otherId).getFriends());
        log.info("Requested common friends between Users {} and {}", id, otherId);

        return common.stream().map(friend -> inMemoryUserStorage.getUser(friend)).collect(Collectors.toList());
    }
}
