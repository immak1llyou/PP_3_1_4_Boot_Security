package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class AppHomeController {

    @GetMapping("/user")
    public String showIndexPageUser() {
        return "user/user_panel";
    }

    @GetMapping("/admin")
    public String showIndexPageAdmin() {
        return "admin/admin_panel";
    }
}
