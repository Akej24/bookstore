package com.bookstoreapplication.bookstore.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class PurchaseRequest {

    @Min(value = 0, message = "User id must not be null and the minimum value is 0")
    @NotNull
    private Long userId;

    private Set<PurchaseDetailRequest> purchaseDetailsRequest;

}
