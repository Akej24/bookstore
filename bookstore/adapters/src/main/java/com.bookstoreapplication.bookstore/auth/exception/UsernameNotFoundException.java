package com.bookstoreapplication.bookstore.auth.exception;

public class UsernameNotFoundException extends AuthenticationException{

    public UsernameNotFoundException() {
        super("Username has not been found");
    }

}
