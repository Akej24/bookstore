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

    @GetMapping("/{bookId}")
    ResponseEntity<UserDatabaseModel> getBookById(@PathVariable long bookId){
        return new ResponseEntity<>(userService.getUserById(bookId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<UserDatabaseModel>> getAllBooks(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<?> deleteBookById(@PathVariable long bookId){
        userService.deleteUser(bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllBooks(){
        userService.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{bookId}")
    ResponseEntity<UserDatabaseModel> updateBookById(@PathVariable long bookId, @RequestBody UserUpdateRequest userUpdateRequest){
        return new ResponseEntity<>(userService.updateUserById(bookId, userUpdateRequest), HttpStatus.OK);
    }

}
