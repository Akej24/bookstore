package com.bookstoreapplication.bookstore.user;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
@AllArgsConstructor
class UserWarmup {

    private final UserHandler userHandler;

    @EventListener
    public void insertStartupData(ContextRefreshedEvent contextRefreshedEvent){
        userHandler.registerUser(new UserCommand(
                new UserEmail("user@test.com"),
                new Username("user"),
                new Password("UserPassword1!"),
                new FirstName("User"),
                new LastName("User"),
                new DateOfBirth(LocalDate.of(2005, Month.MARCH, 1)),
                UserRole.USER
        ));
        userHandler.registerUser(new UserCommand(
                new UserEmail("admin@test.com"),
                new Username("admin"),
                new Password("AdminPassword1!"),
                new FirstName("Admin"),
                new LastName("Admin"),
                new DateOfBirth(LocalDate.of(2000, Month.MAY, 17)),
                UserRole.ADMIN
        ));
    }
}
