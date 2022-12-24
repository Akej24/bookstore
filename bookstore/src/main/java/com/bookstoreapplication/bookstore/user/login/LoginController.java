package com.bookstoreapplication.bookstore.user.login;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    String showLoginForm(HttpSession session) {
        return "login";
    }

}