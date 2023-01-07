package com.bookstoreapplication.bookstore.user.login;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/login")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class LoginController {

    private final LoginService loginService;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    ResponseEntity loginUser(@RequestBody LoginRequest loginRequest){
        if(loginService.startUserSession(loginRequest)){
            logger.info("Successfully logged in");
            return ResponseEntity.ok("Successfully logged in");
        }else{
            logger.info("Unsuccessfully logged in");
            return ResponseEntity.badRequest().build();
        }
    }
}
