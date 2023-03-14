package com.bookstoreapplication.bookstore.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class PurchaseRequest {

    @Min(value = 0, message = "The minimum value of user id is 0")
    @NotNull(message = "User id must not be null")
    private Long userId;

    @Valid
    private Set<PurchaseDetailRequest> purchaseDetailsRequest;

}
