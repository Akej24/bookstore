package com.bookstoreapplication.bookstore.domain.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class FirstName implements Serializable {

        @NotBlank(message="Name must not be blank")
        private String firstName;

}
