package com.bookstoreapplication.bookstore.user.account;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUserById(@PathVariable long userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(required = false) int page){
        page = page>=0 ? page : 0;
        return new ResponseEntity<>(userService.getAllUsers(page), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUserById(@PathVariable long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllUsers(){
        userService.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{userId}")
    ResponseEntity<UserResponse> updateUserById(@PathVariable long userId, @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.updateUserById(userId, userRequest), HttpStatus.OK);
    }

}
