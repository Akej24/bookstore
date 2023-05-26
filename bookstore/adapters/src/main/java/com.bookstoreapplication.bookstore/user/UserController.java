package com.bookstoreapplication.bookstore.user;

import dev.mccue.json.Json;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class UserController {

    private static final int PAGE_SIZE = 10;
    private final UserHandler userHandler;

    @PostMapping("/registration")
    ResponseEntity<?> registerUser(@RequestBody @Valid Json json){
        userHandler.registerUser(UserJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserJsonQueryResponse> getUserById(@PathVariable long userId){
        UserJsonQueryResponse jsonUser = UserJsonQueryResponse.from(userHandler.getUserById(userId));
        return new ResponseEntity<>(jsonUser, HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<UserJsonQueryResponse>> getAllUsers(@RequestParam(required = false) int page){
        page = page>=0 ? page : 0;
        List<UserJsonQueryResponse> jsonUsers = UserJsonQueryResponse.from(userHandler.getAllUsers(PageRequest.of(page, PAGE_SIZE)));
        return new ResponseEntity<>(jsonUsers, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUserById(@PathVariable long userId){
        userHandler.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllUsers(){
        userHandler.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{userId}")
    ResponseEntity<UserQueryResponse> updateUserById(@PathVariable long userId, @RequestBody Json json){
        return new ResponseEntity<>(userHandler.updateUserById(userId, UserJsonUpdateCommand.fromJson(json)), HttpStatus.OK);
    }

}
