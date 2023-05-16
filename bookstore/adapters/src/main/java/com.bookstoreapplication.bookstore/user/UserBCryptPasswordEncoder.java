package com.bookstoreapplication.bookstore.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class UserBCryptPasswordEncoder extends BCryptPasswordEncoder implements IPasswordEncoder{

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

}
