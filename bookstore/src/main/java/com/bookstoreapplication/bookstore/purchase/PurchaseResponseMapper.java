package com.bookstoreapplication.bookstore.purchase;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class PurchaseResponseMapper {

    private PurchaseResponseMapper() {
    }

    static List<PurchaseDto> mapToPurchasesWithDetailsResponse(List<Purchase> purchases){
        return purchases.stream()
                .map(PurchaseResponseMapper::mapToPurchaseWithDetailsResponse)
                .collect(Collectors.toList());
    }

    static PurchaseDto mapToPurchaseWithDetailsResponse(Purchase purchase){
        List<PurchaseDetailDto> detailResponses = mapToDetailsResponse(purchase.getPurchaseDetails());
        return PurchaseDto.builder()
                .purchaseDate(purchase.getPurchaseDate())
                .purchaseDetails(detailResponses)
                .totalPrice(purchase.getTotalPrice())
                .purchaseStatus(purchase.getPurchaseStatus())
                .build();
    }

    static List<PurchaseDetailDto> mapToDetailsResponse(Set<PurchaseDetail> details){
        return details.stream()
                .map(PurchaseResponseMapper::mapToDetailResponse)
                .collect(Collectors.toList());
    }

    static PurchaseDetailDto mapToDetailResponse(PurchaseDetail detail){
        return PurchaseDetailDto.builder()
                .book(BookDtoMapper.mapToBookDto(detail.getBook()))
                .booksAmount(detail.getBooksAmount())
                .build();
    }

    static List<PurchaseDto> mapToPurchasesResponse(List<Purchase> purchases){
        return purchases.stream()
                .map(PurchaseResponseMapper::mapToPurchaseResponse)
                .collect(Collectors.toList());
    }

    static PurchaseDto mapToPurchaseResponse(Purchase purchase){
        return PurchaseDto.builder()
                .purchaseDate(purchase.getPurchaseDate())
                .totalPrice(purchase.getTotalPrice())
                .purchaseStatus(purchase.getPurchaseStatus())
                .build();
    }

}
