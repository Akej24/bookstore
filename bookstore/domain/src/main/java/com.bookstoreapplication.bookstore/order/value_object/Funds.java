package com.bookstoreapplication.bookstore.order.value_object;

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
public class Funds implements Serializable {

        @DecimalMin(value = "0.0", message = "Minimum value of available funds cannot be less than 0.0")
        @NotNull(message = "Available funds must be not null")
        private BigDecimal funds;

}