package com.bookstoreapplication.bookstore.domain.user.value_objects;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Enabled implements Serializable {

        @NotNull(message = "User enabled property cannot be null")
        private Boolean enabled;

}
