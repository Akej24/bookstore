package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.book.value_object.BookAuthor;
import com.bookstoreapplication.bookstore.book.value_object.BookPrice;
import com.bookstoreapplication.bookstore.book.value_object.BookTitle;
import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.cart.CartLine;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "order_details")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderDetailId;
    private UUID orderId;
    private long bookId;
    @Embedded
    private BookTitle bookTitle;
    @Embedded
    private BookAuthor bookAuthor;
    @Embedded
    private BookPrice bookPrice;
    @Embedded
    private BooksAmount booksAmount;

    OrderDetail(CartLine cartLine, UUID orderId) {
        this.orderId = orderId;
        this.bookId = cartLine.getBookProduct().getBookId();
        this.bookTitle = cartLine.getBookProduct().getBookTitle();
        this.bookAuthor = cartLine.getBookProduct().getBookAuthor();
        this.bookPrice = cartLine.getBookProduct().getBookPrice();
        this.booksAmount = cartLine.getAmount();
    }

    static List<OrderDetail> mapFromCart(UUID orderId, Cart customerCart){
        return customerCart.getCartLines()
                .stream()
                .map(line -> new OrderDetail(line, orderId))
                .toList();
    }
}
