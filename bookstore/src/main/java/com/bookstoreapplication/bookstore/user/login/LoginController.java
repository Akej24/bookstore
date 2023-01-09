package com.bookstoreapplication.bookstore.user.login;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
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
    ResponseEntity<HttpSession> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session){
        if(loginService.startUserSession(loginRequest)){
            System.out.println(session.getAttribute("userDatabaseModel"));
            System.out.println(session.getAttribute("userEmail"));
            System.out.println(session.getAttribute("test"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
