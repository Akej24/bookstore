package com.bookstoreapplication.bookstore.user.exception;

public class UserDoesNotExistException extends UserException{

    public UserDoesNotExistException() {
        super("User with given id does not exist");
    }
}
