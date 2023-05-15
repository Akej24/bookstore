package com.bookstoreapplication.bookstore.auth.exception;

public class JwtNofFoundInRequestHeaderException extends AuthenticationException{

    public JwtNofFoundInRequestHeaderException() {
        super("Jwt has not been found in request header");
    }
}
