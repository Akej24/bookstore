package com.bookstoreapplication.bookstore.user.exception;

public class EmailTakenException extends UserException{

    public EmailTakenException() {
        super("This e-mail is already taken");
    }

}
