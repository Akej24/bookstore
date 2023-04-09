package com.bookstoreapplication.bookstore.domain.purchase.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PurchaseDetailId implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long purchaseDetailId;

}
