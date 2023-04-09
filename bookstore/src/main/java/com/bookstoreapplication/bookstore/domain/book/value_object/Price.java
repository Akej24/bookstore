package com.bookstoreapplication.bookstore.domain.book.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Price implements Serializable {

        @DecimalMin(value = "0.0", message = "The minimum value of the price is 0.0")
        @NotNull(message = "Price must be not null")
        private BigDecimal price;

}
