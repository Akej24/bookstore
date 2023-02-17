package com.bookstoreapplication.bookstore.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @NotNull
    private long userId;
    @NotNull
    private long bookId;
    @NotNull
    private int booksAmount;
}
