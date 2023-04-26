package com.bookstoreapplication.bookstore.book.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookId implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long bookId;

}
