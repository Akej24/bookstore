package com.bookstoreapplication.bookstore.purchase.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PhoneNumber {

    @NotBlank(message = "Phone number must be not blank")
    @Pattern(regexp = "[1-9]{9}", message = "Phone number can only contain 9 digits from 1 to 9")
    private String phoneNumber;

}
