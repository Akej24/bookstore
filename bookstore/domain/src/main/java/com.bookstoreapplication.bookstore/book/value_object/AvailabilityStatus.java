package com.bookstoreapplication.bookstore.book.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class AvailabilityStatus implements Serializable {

        @NotNull(message = "Status mut not be null")
        private Boolean availabilityStatus;

}
