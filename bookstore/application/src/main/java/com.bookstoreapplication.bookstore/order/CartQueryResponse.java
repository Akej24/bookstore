package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.order.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.order.value_object.TotalPrice;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class CartQueryResponse {

    @JsonUnwrapped
    private List<CartLineQueryResponse> cartLines;
    @JsonUnwrapped
    private TotalPrice totalPrice;

    static CartQueryResponse toResponse(Cart source) {
        return new CartQueryResponse(
                CartLineQueryResponse.mapToCartLinesQueryResponse(source.getCartLines()),
                source.getTotalPrice()
        );
    }

    @Getter
    @AllArgsConstructor
    static class CartLineQueryResponse{

        @JsonUnwrapped
        private BookProductQueryResponse bookProduct;
        @JsonUnwrapped
        private BooksAmount amount;

        static List<CartLineQueryResponse> mapToCartLinesQueryResponse(List<CartLine> cartLines){
            return cartLines.stream()
                    .map( cartLine -> new CartLineQueryResponse(
                            BookProductQueryResponse.mapToBookProductQueryResponse(cartLine.getBookProduct()),
                            cartLine.getAmount()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    static class BookProductQueryResponse{

        @JsonUnwrapped
        private BookTitle bookTitle;
        @JsonUnwrapped
        private BookAuthor bookAuthor;
        @JsonUnwrapped
        private AvailabilityStatus availabilityStatus;
        @JsonUnwrapped
        private AvailablePieces availablePieces;
        @JsonUnwrapped
        private BookPrice bookPrice;

        static BookProductQueryResponse mapToBookProductQueryResponse(BookProduct source){
            return new BookProductQueryResponse(
                    source.getBookTitle(),
                    source.getBookAuthor(),
                    source.getAvailabilityStatus(),
                    source.getAvailablePieces(),
                    source.getBookPrice()
            );
        }

    }
}
