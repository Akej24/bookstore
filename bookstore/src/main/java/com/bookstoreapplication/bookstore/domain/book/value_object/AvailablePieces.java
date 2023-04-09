package com.bookstoreapplication.bookstore.domain.book.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class AvailablePieces implements Serializable {

        @Min(value = 0, message = "The minimum value of available pieces is 0")
        @NotNull(message = "Available pieces must be not null")
        private Integer availablePieces;

}
