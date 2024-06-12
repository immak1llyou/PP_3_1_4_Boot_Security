package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RESTController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 @RequestParam(name = "roles", required = false) List<String> roles) {
        userService.saveUser(user, roles);
        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(name = "id") Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User updateUser,
                                                 @RequestParam(name = "roles", required = false) List<String> roles) {
        userService.updateUser(updateUser, roles);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException ex) {
        PersonErrorResponse response =
                new PersonErrorResponse("User with id wasn't found!", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
