package ru.yandex.practicum.filmorate.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public User addFriend(Integer id, Integer friendId) {
        friendshipStorage.addFriend(id, friendId);
        log.info("User id = {} added friend id = {}", id, friendId);
        return userStorage.getUser(id);
    }

    public User deleteFriend(Integer id, Integer friendId) {
        friendshipStorage.deleteFriend(id, friendId);
        log.info("Users {} and {} are no longer friends", id, friendId);
        return userStorage.getUser(id);
    }

    public List<User> getFriends(Integer id) {
        log.info("Requested friend list of User {}", id);
        return friendshipStorage.getFriend(id);
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        log.info("Requested common friends between Users {} and {}", id, otherId);
        return friendshipStorage.getCommon(id, otherId);
    }
}
