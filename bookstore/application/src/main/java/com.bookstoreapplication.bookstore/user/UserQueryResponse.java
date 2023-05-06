package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.order.value_object.Funds;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class UserQueryResponse {

    @JsonUnwrapped
    private UserEmail email;
    @JsonUnwrapped
    private Username username;
    @JsonUnwrapped
    private FirstName firstName;
    @JsonUnwrapped
    private LastName lastName;
    @JsonUnwrapped
    private DateOfBirth dateOfBirth;
    @JsonUnwrapped
    private Funds funds;

    static Set<UserQueryResponse> toResponses(List<User> sources){
        return sources.stream().map(UserQueryResponse::toResponse).collect(Collectors.toSet());
    }

    static UserQueryResponse toResponse(User source){
        return new UserQueryResponse(
                source.getEmail(),
                source.getUsername(),
                source.getFirstName(),
                source.getLastName(),
                source.getDateOfBirth(),
                source.getFunds());
    }

}
