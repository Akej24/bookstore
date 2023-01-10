package com.bookstoreapplication.bookstore.user.registration.exception;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException() {
        super(
                "Unsuccessfully registered - the password must contain 8-16 characters, one lowercase letter, one uppercase letter, a special character and a number, email must be not taken and fields must be not blank"
        );
    }

}
