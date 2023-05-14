package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import com.bookstoreapplication.bookstore.user.value_objects.Username;

public class UserFacade {


    public static Username getUsernameByCustomerId(long customerId) {
        return new Username("Gepardzica1232");
    }

    public static UserEmail getUserEmailByCustomerId(long customerId) {
        return new UserEmail(("gepardzica1232@gmail.com"));
    }
}
