package com.bookstoreapplication.bookstore.user.login;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/login")
@AllArgsConstructor
class LoginController {

    private final LoginService loginService;

    @GetMapping
    String showLoginForm(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping
    String loginUser(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model){
        loginService.startUserSession(loginRequest);
        model.addAttribute("message", "Successfully");
        return "login";
    }
}
