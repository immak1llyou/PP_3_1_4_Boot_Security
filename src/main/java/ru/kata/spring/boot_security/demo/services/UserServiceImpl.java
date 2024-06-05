package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public void deleteUserByUserName(String userName) {
        Optional<User> findUser = userRepository.findByUserName(userName);
        if (findUser.isPresent()) {
            User user = findUser.get();
            userRepository.delete(user);
        }
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new UsernameNotFoundException(String.format("Пользователь '%s' уже существует." +
                    " Сохранение невозможно.", user.getUserName()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser, int id) {
        User existingUser = userRepository.findById(updateUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        existingUser.getRole().clear();
        existingUser.setRole(updateUser.getRole());
        if (!existingUser.getPassword().equals(updateUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        existingUser.setYearOfBirth(updateUser.getYearOfBirth());
        existingUser.setUserName(updateUser.getUserName());
        existingUser.setName(updateUser.getName());
        existingUser.setSurname(updateUser.getSurname());
        existingUser.setEmail(updateUser.getEmail());
        userRepository.save(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getAuthUser() {
        Optional<User> user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.orElse(null);
    }
}
