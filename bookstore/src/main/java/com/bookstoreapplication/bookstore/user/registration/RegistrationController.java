package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    ResponseEntity<UserDatabaseModel> registerUser(@RequestBody RegistrationRequest registrationRequest, HttpServletRequest request){
        var registeredUser = registrationService.registerUserIfPasswordValidAndEmailNotTaken(registrationRequest);
        HttpSession session = request.getSession();

        session.setAttribute("test", "test");
        session.setAttribute("userDatabaseModel", registeredUser);
        session.setAttribute("userEmail", registeredUser.getEmail());
        System.out.println(session.getAttribute("userDatabaseModel"));
        System.out.println(session.getAttribute("userEmail"));
        System.out.println(session.getAttribute("test"));

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }

}
