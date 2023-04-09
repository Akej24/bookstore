package com.bookstoreapplication.bookstore.domain.purchase.core;

import com.bookstoreapplication.bookstore.domain.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.application.purchase.PurchaseCommandDetail;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.SimplePurchaseId;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PurchaseService {

    public Purchase placeOrder(Set<PurchaseCommandDetail> commandDetails,
                               Customer customer,
                               List<Purchase> customerInitializedPurchases,
                               Map<BookProduct, BooksAmount> orderedBooks) {

        Purchase purchase = new Purchase(customer, customerInitializedPurchases);

        orderedBooks.forEach(BookProduct::decreaseAvailablePieces);

        Set<PurchaseDetail> details = commandDetails.stream()
                .map(commandDetail -> new PurchaseDetail(commandDetail.bookId(),
                        commandDetail.booksAmount(),
                        new SimplePurchaseId(purchase.getPurchaseId().getPurchaseId())))
                .collect(Collectors.toSet());

        return purchase;
    }


}
