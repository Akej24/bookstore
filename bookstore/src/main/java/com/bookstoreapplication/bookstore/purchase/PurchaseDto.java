package com.bookstoreapplication.bookstore.purchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
class PurchaseDto {

    private LocalDateTime purchaseDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PurchaseDetailDto> purchaseDetails;
    private Double totalPrice;
    private PurchaseStatus purchaseStatus;

}
