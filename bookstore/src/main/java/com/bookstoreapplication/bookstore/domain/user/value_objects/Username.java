package com.bookstoreapplication.bookstore.domain.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Username implements Serializable {

        @NotBlank(message="Username must not be blank")
        private String username;

}
