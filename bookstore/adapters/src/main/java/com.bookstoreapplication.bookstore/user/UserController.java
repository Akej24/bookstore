package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.UserCommand;
import com.bookstoreapplication.bookstore.user.UserCommandHandler;
import com.bookstoreapplication.bookstore.user.UserQueryResponse;
import com.bookstoreapplication.bookstore.user.UserUpdateCommand;
import com.bookstoreapplication.bookstore.user.value_objects.SimpleUserId;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class UserController {

    private static final int PAGE_SIZE = 10;
    private final UserCommandHandler userCommandHandler;

    @PostMapping
    ResponseEntity<?> registerUser(@RequestBody @Valid UserCommand userCommand){
        userCommandHandler.registerUserIfPasswordValidAndEmailNotTaken(userCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserQueryResponse> getUserById(@PathVariable @Valid SimpleUserId userId){
        return new ResponseEntity<>(userCommandHandler.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<Set<UserQueryResponse>> getAllUsers(@RequestParam(required = false) int page){
        page = page>=0 ? page : 0;
        return new ResponseEntity<>(userCommandHandler.getAllUsers(PageRequest.of(page, PAGE_SIZE)), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUserById(@PathVariable @Valid SimpleUserId userId){
        userCommandHandler.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllUsers(){
        userCommandHandler.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{userId}")
    ResponseEntity<UserQueryResponse> updateUserById(@Valid SimpleUserId userId, @RequestBody UserUpdateCommand userUpdateCommand){
        return new ResponseEntity<>(userCommandHandler.updateUserById(userId, userUpdateCommand), HttpStatus.OK);
    }

}
