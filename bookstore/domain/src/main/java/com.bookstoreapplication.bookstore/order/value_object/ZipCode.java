package com.bookstoreapplication.bookstore.order.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class ZipCode {

    @NotBlank(message = "Zip code must be not blank")
    @Pattern(regexp="^[0-9]{2}-[0-9]{3}$")
    private String zipCode;

}
