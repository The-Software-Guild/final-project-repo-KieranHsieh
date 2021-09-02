package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.User;

import java.util.List;

public interface UserDao {
    User getUserById(String id);
    void addUser(User user);
    void removeUserById(String id);
    void updateUser(User user);
    List<User> getAllUsers();
    void removeExpiredUsers();
}
