package com.bookstoreapplication.bookstore.book.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class BookAuthor implements Serializable {

        @NotBlank(message = "Book author must not be blank")
        private String bookAuthor;

}
