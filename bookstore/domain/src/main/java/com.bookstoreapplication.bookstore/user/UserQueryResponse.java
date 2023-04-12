package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.user.core.User;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserQueryResponse {

    private UserEmail email;
    private Username username;
    private Password password;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private Funds funds;

    static Set<UserQueryResponse> toResponses(List<User> sources){
        return sources.stream().map(UserQueryResponse::toResponse).collect(Collectors.toSet());
    }

    static UserQueryResponse toResponse(User source){
        return new UserQueryResponse(
                source.getEmail(),
                source.getUsername(),
                source.getPassword(),
                source.getFirstName(),
                source.getLastName(),
                source.getDateOfBirth(),
                source.getFunds());
    }

}
