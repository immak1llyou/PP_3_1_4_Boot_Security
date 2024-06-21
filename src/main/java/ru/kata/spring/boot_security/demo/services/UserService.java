package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User getUserByUserName(String userName);

    User getUserById(Integer id);

    void deleteUserById(Integer id);

    void saveUser(User user);

    void updateUser(User updateUser, Integer id);

    List<User> getAllUsers();
}
