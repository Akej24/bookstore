package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/registration")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    ResponseEntity<UserDatabaseModel> registerUser(@RequestBody RegistrationRequest registrationRequest){
        var registeredUser = registrationService.registerUserIfPasswordValidAndEmailNotTaken(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }

}
