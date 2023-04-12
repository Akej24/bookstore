package com.bookstoreapplication.bookstore.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Locked implements Serializable {

        @NotNull(message = "User locked property cannot be null")
        private Boolean locked;

}