package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUser = userDao.findByUserName(username);
        if (findUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(findUser.get());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
        if (userDao.findByUserName(userName).isEmpty()) {
            throw new UsernameNotFoundException(String.format("Пользователь с именем '%s' не найден в базе данных."
                    , userName));
        }
        return userDao.findByUserName(userName).get();
    }

    @Override
    @Transactional
    public void deleteUserByUserName(String userName) {
        Optional<User> findUser = userDao.findByUserName(userName);
        if (findUser.isPresent()) {
            User user = findUser.get();
            userDao.delete(user);
        }
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (userDao.findByUserName(user.getUserName()).isPresent()) {
            throw new UsernameNotFoundException(String.format("Пользователь '%s' уже существует." +
                    " Сохранение невозможно.", user.getUserName()));
        }
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser, int id) {
        User existingUser = userDao.findById(updateUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        existingUser.getRole().clear();
        existingUser.setRole(updateUser.getRole());
        existingUser.setPassword(updateUser.getPassword());
        existingUser.setYearOfBirth(updateUser.getYearOfBirth());
        existingUser.setUserName(updateUser.getUserName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
