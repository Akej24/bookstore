package com.bookstoreapplication.bookstore.user.login.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User with given email and password does not exist");
    }
}
