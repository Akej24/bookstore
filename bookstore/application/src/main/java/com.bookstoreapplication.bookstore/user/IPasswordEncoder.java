package com.bookstoreapplication.bookstore.user;

interface IPasswordEncoder {

    String encode(CharSequence rawPassword);

}
