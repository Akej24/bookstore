package com.bookstoreapplication.bookstore.auth.exception;

public class InvalidPasswordForGivenEmailException extends AuthenticationException{

    public InvalidPasswordForGivenEmailException() {
        super("Invalid password for given e-mail");
    }
}
