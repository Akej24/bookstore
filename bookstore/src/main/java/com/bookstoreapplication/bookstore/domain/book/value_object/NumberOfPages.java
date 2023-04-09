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
public class NumberOfPages implements Serializable {

        @Min(value = 1, message = "The minimum value of number of pages is 1")
        @NotNull(message = "Number of pages must be not null")
        private Integer numberOfPages;

}
