package com.bookstoreapplication.bookstore.user.login;

import com.bookstoreapplication.bookstore.config.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/login")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;

    @PostMapping
    ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        var loggedUser = loginService.loginUser(loginRequest);
        var jwtToken = jwtService.generateToken(loggedUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loggedUser);
    }
}
