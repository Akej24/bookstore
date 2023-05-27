package com.bookstoreapplication.bookstore.auth.login;

import com.bookstoreapplication.bookstore.user.value_objects.Password;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.mccue.json.Json;
import dev.mccue.json.JsonDecoder;
import dev.mccue.json.JsonEncodable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class LoginJsonCommand implements JsonEncodable {

    private final UserEmail email;
    private final Password password;

    @JsonCreator
    public static LoginCommand fromJson(Json json) {
        return new LoginCommand(
                JsonDecoder.field(json, "email", email ->
                        new UserEmail(JsonDecoder.string(email))),

                JsonDecoder.field(json, "password", password ->
                        new Password(JsonDecoder.string(password)))
        );
    }

    @Override
    public Json toJson() {
        return Json.objectBuilder()
                .put("email", Json.of(email.getEmail()))
                .put("password", Json.of(password.getPassword()))
                .build();
    }

}
