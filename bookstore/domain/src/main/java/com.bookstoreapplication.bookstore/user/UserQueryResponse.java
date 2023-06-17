package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class UserQueryResponse {

    private final long userId;
    private final UserEmail email;
    private final Username username;
    private final FirstName firstName;
    private final LastName lastName;
    private final DateOfBirth dateOfBirth;
    private final Funds funds;

    static List<UserQueryResponse> from(List<User> sources){
        return sources.stream()
                .map(UserQueryResponse::from)
                .collect(Collectors.toList());
    }

    static UserQueryResponse from(User source){
        return new UserQueryResponse(
                source.getUserId(),
                source.getEmail(),
                source.getUsername(),
                source.getFirstName(),
                source.getLastName(),
                source.getDateOfBirth(),
                source.getFunds());
    }

}
