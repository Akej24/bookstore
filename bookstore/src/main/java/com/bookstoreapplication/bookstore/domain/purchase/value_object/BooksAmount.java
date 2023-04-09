package com.bookstoreapplication.bookstore.domain.purchase.value_object;

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
public class BooksAmount implements Serializable {

        @Min(value = 1, message = "The minimum value of books amount is 1")
        @NotNull(message = "Books amount cannot be null")
        private Integer booksAmount;

}
