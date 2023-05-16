package com.bookstoreapplication.bookstore.order.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class City {

    @NotBlank(message = "City must be not null")
    private String city;

}
