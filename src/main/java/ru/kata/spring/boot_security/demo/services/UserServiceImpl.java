package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    public void deleteUserById(Integer id) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void saveUser(User user, List<String> roles) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new UsernameNotFoundException(String.format("Пользователь c ID= '%s' уже существует." +
                    " Сохранение невозможно.", user.getUserName()));
        }
        user.setRole(roleService.getRoles().stream().filter(role -> roles.contains(role.getRoleName())).toList());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser, List<String> roles) {
        updateUser.setRole(roleService.getRoles().stream().filter(role -> roles.contains(role.getRoleName())).toList());
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        userRepository.save(updateUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
