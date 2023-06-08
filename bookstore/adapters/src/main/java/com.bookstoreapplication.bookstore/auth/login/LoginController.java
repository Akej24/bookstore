package com.bookstoreapplication.bookstore.auth.login;

import dev.mccue.json.Json;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/login")
@AllArgsConstructor
class LoginController {

    private final LoginHandler loginHandler;

    @PostMapping
    ResponseEntity<?> loginUser(@RequestBody Json json) {
        String jwtToken = loginHandler.loginUser(LoginJsonCommand.fromJson(json));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
}
