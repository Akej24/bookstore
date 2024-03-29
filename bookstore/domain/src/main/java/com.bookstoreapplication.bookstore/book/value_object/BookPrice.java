package com.bookstoreapplication.bookstore.book.value_object;

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
public class BookPrice implements Serializable {

        @DecimalMin(value = "0.00", message = "The minimum value of the price is 0.00")
        @NotNull(message = "Book price must be not null")
        private BigDecimal bookPrice;

}
