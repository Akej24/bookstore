package com.bookstoreapplication.bookstore.user.exception;

public class InvalidPasswordException extends UserException{

    public InvalidPasswordException() {
        super("Invalid password - password must contain 8-16 characters, " +
                "1 uppercase, 1 lowercase, 1 digit and 1 special character " +
                "and cannot have any whitespaces characters");
    }

}
