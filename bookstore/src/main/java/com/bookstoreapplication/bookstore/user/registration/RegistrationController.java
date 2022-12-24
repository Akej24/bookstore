package com.bookstoreapplication.bookstore.user.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "/register")
@AllArgsConstructor
class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping
    String showRegistrationForm(Model model){
        model.addAttribute("userToRegister", new RegistrationRequest());
        return "registration";
    }

    @PostMapping
    String registerUser(@ModelAttribute("userToRegister") RegistrationRequest request, Model model){
        registrationService.registerUser(request);
        model.addAttribute("message", "Successfully");
        return "registration";

    }
}
