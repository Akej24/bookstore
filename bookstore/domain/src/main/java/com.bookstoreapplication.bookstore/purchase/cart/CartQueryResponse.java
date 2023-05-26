package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CartQueryResponse {

    private final List<CartLineQueryResponse> cartLines;
    private final TotalPrice totalPrice;

    static CartQueryResponse toResponse(Cart source) {
        return new CartQueryResponse(
                CartLineQueryResponse.toResponses(source.getCartLines()),
                source.getTotalPrice()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class CartLineQueryResponse{

        private final BookProductQueryResponse bookProduct;
        private final BooksAmount amount;

        static List<CartLineQueryResponse> toResponses(List<CartLine> cartLines){
            return cartLines.stream()
                    .map( cartLine -> new CartLineQueryResponse(
                            BookProductQueryResponse.toResponse(cartLine.getBookProduct()),
                            cartLine.getAmount()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class BookProductQueryResponse{

        private final BookTitle bookTitle;
        private final BookAuthor bookAuthor;
        private final AvailabilityStatus availabilityStatus;
        private final AvailablePieces availablePieces;
        private final BookPrice bookPrice;

        static BookProductQueryResponse toResponse(BookProduct source){
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
