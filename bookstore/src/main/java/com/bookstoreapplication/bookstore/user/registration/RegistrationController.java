package com.bookstoreapplication.bookstore.user.registration;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/registration")
@AllArgsConstructor
class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody @Valid RegistrationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "register";
        return registrationService.register(request);
    }
}
