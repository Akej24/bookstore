package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.config.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/registration")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class RegistrationController {

    private final RegistrationService registrationService;
    private final JwtService jwtService;

    @PostMapping
    ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest){
        var registeredUser = registrationService.registerUserIfPasswordValidAndEmailNotTaken(registrationRequest);
        var jwtToken = jwtService.generateToken(registeredUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(registeredUser);
    }

}
