package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.util.PersonNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
        if (userRepository.findByUserName(userName).isEmpty()) {
            throw new UsernameNotFoundException(String.format("Пользователь с именем '%s' не найден в базе данных."
                    , userName));
        }
        return userRepository.findByUserName(userName).get();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new UsernameNotFoundException(String.format("Пользователь c ID= '%s' уже существует." +
                    " Сохранение невозможно.", user.getUserName()));
        }
        user.setRole(roleService.getRoles().stream()
                .filter(role -> user.getRole().stream().anyMatch(r -> r.getRoleName().equals(role.getRoleName())))
                .collect(Collectors.toList()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser, Integer id) {
        updateUser.setId(id);
        updateUser.setRole(roleService.getRoles().stream()
                .filter(role -> updateUser.getRole().stream().anyMatch(r -> r.getRoleName().equals(role.getRoleName())))
                .collect(Collectors.toList()));
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        userRepository.save(updateUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
