package com.bookstoreapplication.bookstore.order.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PhoneNumber {

    @NotBlank(message = "Phone number must be not blank")
    @Size(min = 9, max = 9)
    private String phoneNumber;

}
