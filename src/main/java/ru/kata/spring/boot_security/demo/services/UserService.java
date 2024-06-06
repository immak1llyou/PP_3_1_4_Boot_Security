package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User getUserByUserName(String userName);

    void deleteUserByUserName(String userName);

    void saveUser(User user, List<String> roles);

    void updateUser(User updateUser, int id, List<String> roles);

    List<User> getAllUsers();

    User getAuthUser();
}
