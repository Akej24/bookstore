package com.bookstoreapplication.bookstore.auth.exception;

public class UserEmailHasNotBeenFoundException extends AuthenticationException{

    public UserEmailHasNotBeenFoundException() {
        super("User with given e-mail has not been found");
    }
}
