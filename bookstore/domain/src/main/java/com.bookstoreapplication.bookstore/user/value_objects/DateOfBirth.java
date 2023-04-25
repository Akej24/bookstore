package com.bookstoreapplication.bookstore.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class DateOfBirth implements Serializable {

        @NotNull(message = "Date of birth must not be null")
        private LocalDate dateOfBirth;

}
