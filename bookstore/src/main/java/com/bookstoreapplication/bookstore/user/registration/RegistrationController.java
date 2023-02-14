package com.bookstoreapplication.bookstore.user.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/registration")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest){
        var registeredUser = registrationService.registerUserIfPasswordValidAndEmailNotTaken(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

}
