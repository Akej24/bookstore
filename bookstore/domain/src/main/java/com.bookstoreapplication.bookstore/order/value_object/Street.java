package com.bookstoreapplication.bookstore.order.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Street {

    @NotBlank(message = "Street must be not null")
    private String street;

}
