package com.bookstoreapplication.bookstore.domain.purchase.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class TotalPrice implements Serializable {

        @DecimalMin(value = "0.0", message = "The minimum value of total price is 0.0")
        private BigDecimal totalPrice;

}