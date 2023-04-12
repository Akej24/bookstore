package com.bookstoreapplication.bookstore.purchase.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class SimplePurchaseId implements Serializable {

    @NotNull(message = "Purchase id cannot be null")
    private long purchaseId;

}
