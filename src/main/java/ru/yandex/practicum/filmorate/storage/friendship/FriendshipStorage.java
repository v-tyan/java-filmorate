package ru.yandex.practicum.filmorate.storage.friendship;

import java.util.List;

import ru.yandex.practicum.filmorate.model.User;

public interface FriendshipStorage {
    List<User> getFriend(Integer id);

    List<User> getCommon(Integer id, Integer friendId);

    void deleteFriend(Integer id, Integer friendId);

    void addFriend(Integer id, Integer friendId);
}
