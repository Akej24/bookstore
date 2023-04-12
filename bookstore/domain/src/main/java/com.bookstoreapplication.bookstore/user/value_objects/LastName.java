package com.bookstoreapplication.bookstore.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class LastName implements Serializable {

        @NotBlank(message="Surname must not be blank")
        private String lastName;

}
