package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPanel(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("authUser", userService.getAuthUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("activeTable", "usersTable");
        return "admin/adminPanel";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("updateUser") User updateUser, @RequestParam("id") int id,
                             @RequestParam(value = "roleStringNameArray", required = false) List<String> roleArray) {
        userService.updateUser(updateUser, id, roleArray);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userName") String userName) {
        userService.deleteUserByUserName(userName);
        return "redirect:/admin";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User newUser,
                          @RequestParam(value = "roleStringNameArray", required = false) List<String> roleArray) {
        userService.saveUser(newUser, roleArray);
        return "redirect:/admin";
    }
}