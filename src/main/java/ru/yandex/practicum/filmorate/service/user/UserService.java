package ru.yandex.practicum.filmorate.service.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage inMemoryUserStorage;

    public User addFriend(Integer id, Integer friendId) {
        User interimUser = inMemoryUserStorage.getUser(id);
        inMemoryUserStorage.getUser(friendId).getFriends().add(id);
        interimUser.getFriends().add(friendId);
        return interimUser;
    }

    public User deleteFriend(Integer id, Integer friendId) {
        User interimUser = inMemoryUserStorage.getUser(id);
        if (!interimUser.getFriends().contains(friendId)) {
            throw new UserNotFoundException("User not found");
        }
        interimUser.getFriends().remove(friendId);
        inMemoryUserStorage.getUser(friendId).getFriends().remove(id);
        return interimUser;
    }

    public List<User> getFriends(Integer id) {
        return inMemoryUserStorage.getUser(id).getFriends().stream()
                .map(friend -> inMemoryUserStorage.getUser(friend))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        Set<Integer> common = new HashSet<>(inMemoryUserStorage.getUser(id).getFriends());
        common.retainAll(inMemoryUserStorage.getUser(otherId).getFriends());

        return common.stream().map(friend -> inMemoryUserStorage.getUser(friend)).collect(Collectors.toList());
    }
}
