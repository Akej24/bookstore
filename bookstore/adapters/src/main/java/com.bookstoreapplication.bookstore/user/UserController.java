package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.auth.core.JwtFacade;
import dev.mccue.json.Json;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
class UserController {

    private static final int PAGE_SIZE = 10;
    private final JwtFacade jwtFacade;
    private final UserHandler userHandler;

    @PostMapping("/users/registration")
    ResponseEntity<?> registerUser(@RequestBody @Valid Json json){
        userHandler.registerUser(UserJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<UserJsonQueryResponse> getUserById(@PathVariable long userId){
        var jsonUser = UserJsonQueryResponse.from(userHandler.getUserById(userId));
        return new ResponseEntity<>(jsonUser, HttpStatus.OK);
    }

    @GetMapping("/account")
    ResponseEntity<UserJsonQueryResponse> getUserById(HttpServletRequest request){
        long userId = jwtFacade.extractUserId(request);
        var jsonUser = UserJsonQueryResponse.from(userHandler.getUserById(userId));
        return new ResponseEntity<>(jsonUser, HttpStatus.OK);
    }

    @GetMapping("/users")
    ResponseEntity<List<UserJsonQueryResponse>> getAllUsers(@RequestParam(required = false) int page){
        page = page>=0 ? page : 0;
        List<UserJsonQueryResponse> jsonUsers = UserJsonQueryResponse.from(userHandler.getAllUsers(PageRequest.of(page, PAGE_SIZE)));
        return new ResponseEntity<>(jsonUsers, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<?> deleteUserById(@PathVariable long userId){
        userHandler.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/users")
    ResponseEntity<?> deleteAllUsers(){
        userHandler.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/account")
    ResponseEntity<?> updateUserById(HttpServletRequest request, @RequestBody Json json){
        long userId = jwtFacade.extractUserId(request);
        userHandler.updateUserById(userId, UserJsonUpdateCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/users/{userId}")
    ResponseEntity<UserQueryResponse> updateUserById(@PathVariable long userId, @RequestBody Json json){
        userHandler.updateUserById(userId, UserJsonUpdateCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
