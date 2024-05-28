package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "admin/admin";
    }

    @GetMapping("/show/edit")
    public String editUser(Model model, @RequestParam("userName") String userName) {
        model.addAttribute("user", userServiceImpl.getUserByUserName(userName));
        model.addAttribute("roles", roleServiceImpl.getRoles());
        return "admin/edit_users_page";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userServiceImpl.updateUser(user, id);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userName") String userName) {
        userServiceImpl.deleteUserByUserName(userName);
        return "redirect:/admin";
    }

    @GetMapping("/show")
    public String showInfo(@RequestParam("userName") String userName, Model model) {
        model.addAttribute("user", userServiceImpl.getUserByUserName(userName));
        return "admin/show_user_info_page";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleServiceImpl.getRoles());
        return "admin/new_user_page";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }
}
