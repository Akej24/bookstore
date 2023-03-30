package com.bookstoreapplication.bookstore.purchase;


import com.bookstoreapplication.bookstore.book.BookDtoMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class PurchaseResponseMapper {

    private PurchaseResponseMapper() {
    }

    static List<PurchaseResponse> mapToPurchasesWithDetailsResponse(List<Purchase> purchases){
        return purchases.stream()
                .map(PurchaseResponseMapper::mapToPurchaseWithDetailsResponse)
                .collect(Collectors.toList());
    }

    static PurchaseResponse mapToPurchaseWithDetailsResponse(Purchase purchase){
        List<PurchaseDetailResponse> detailResponses = mapToDetailsResponse(purchase.getPurchaseDetails());

        return PurchaseResponse.builder()
                .purchaseDate(purchase.getPurchaseDate())
                .purchaseDetails(detailResponses)
                .totalPrice(purchase.getTotalPrice())
                .purchaseStatus(purchase.getPurchaseStatus())
                .build();
    }

    static List<PurchaseDetailResponse> mapToDetailsResponse(Set<PurchaseDetail> details){
        return details.stream()
                .map(PurchaseResponseMapper::mapToDetailResponse)
                .collect(Collectors.toList());
    }

    static PurchaseDetailResponse mapToDetailResponse(PurchaseDetail detail){
        return PurchaseDetailResponse.builder()
                .book(BookDtoMapper.mapToBookDto(detail.getBook()))
                .booksAmount(detail.getBooksAmount())
                .build();
    }

    static List<PurchaseResponse> mapToPurchasesResponse(List<Purchase> purchases){
        return purchases.stream()
                .map(PurchaseResponseMapper::mapToPurchaseResponse)
                .collect(Collectors.toList());
    }

    static PurchaseResponse mapToPurchaseResponse(Purchase purchase){
        return PurchaseResponse.builder()
                .purchaseDate(purchase.getPurchaseDate())
                .totalPrice(purchase.getTotalPrice())
                .purchaseStatus(purchase.getPurchaseStatus())
                .build();
    }

}
