package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CartJsonQueryResponse {

    @JsonUnwrapped
    private final List<CartLineJsonQueryResponse> cartLines;
    @JsonUnwrapped
    private final TotalPrice totalPrice;

    static CartJsonQueryResponse from(CartQueryResponse source) {
        return new CartJsonQueryResponse(
                CartLineJsonQueryResponse.from(source.getCartLines()),
                source.getTotalPrice()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class CartLineJsonQueryResponse{

        @JsonUnwrapped
        private final BookProductJsonQueryResponse bookProduct;
        @JsonUnwrapped
        private final BooksAmount amount;

        static List<CartLineJsonQueryResponse> from(List<CartQueryResponse.CartLineQueryResponse> sources){
            return sources.stream()
                    .map( source -> new CartLineJsonQueryResponse(
                            BookProductJsonQueryResponse.from(source.getBookProduct()),
                            source.getAmount()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class BookProductJsonQueryResponse{

        private final long bookId;
        @JsonUnwrapped
        private final BookTitle bookTitle;
        @JsonUnwrapped
        private final BookAuthor bookAuthor;
        @JsonUnwrapped
        private final AvailabilityStatus availabilityStatus;
        @JsonUnwrapped
        private final AvailablePieces availablePieces;
        @JsonUnwrapped
        private final BookPrice bookPrice;

        static BookProductJsonQueryResponse from(CartQueryResponse.BookProductQueryResponse source){
            return new BookProductJsonQueryResponse(
                    source.getBookId(),
                    source.getBookTitle(),
                    source.getBookAuthor(),
                    source.getAvailabilityStatus(),
                    source.getAvailablePieces(),
                    source.getBookPrice()
            );
        }
    }
}
