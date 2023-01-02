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
    private static final String INVALID_PASSWORD_OR_EMAIL_MSG = "The password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number and email must be not taken";

    @GetMapping
    String showRegistrationForm(Model model){
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registration";
    }

    @PostMapping
    String registerUser(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest, Model model){
        if(registrationService.registerUserIfPasswordValidAndEmailNotTaken(registrationRequest)){
            model.addAttribute("message", "Successfully");
        }else {
            model.addAttribute("message", INVALID_PASSWORD_OR_EMAIL_MSG);
        }
        return "registration";
    }
}
