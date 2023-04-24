package com.bookstoreapplication.bookstore.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class EncodedPassword implements Serializable {

    @NotBlank(message="Password must not be blank")
    private String encodedPassword;

}
