package com.bookstoreapplication.bookstore.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class UserEmail implements Serializable {

        @Email(message = "Invalid e-mail format")
        private String email;

}
