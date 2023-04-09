package com.bookstoreapplication.bookstore.domain.book.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Title implements Serializable {

        @NotBlank(message = "Title must not be blank")
        private String title;

}
