package com.bookstoreapplication.bookstore.auth;

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

    @PostMapping
    ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        String jwtToken = loginService.loginUser(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

}
