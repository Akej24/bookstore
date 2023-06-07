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
public class BookTitle implements Serializable{

    @NotBlank(message = "Book title must not be blank")
    private String bookTitle;

}
