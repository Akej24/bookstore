package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.book.value_object.Author;
import com.bookstoreapplication.bookstore.book.value_object.Price;
import com.bookstoreapplication.bookstore.book.value_object.Title;
import com.bookstoreapplication.bookstore.order.value_object.BooksAmount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "order_details")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderDetailId;
    private long orderId;
    private long bookId;
    @Embedded
    private Title bookTitle;
    @Embedded
    private Author bookAuthor;
    @Embedded
    private Price bookPrice;
    @Embedded
    private BooksAmount booksAmount;

    OrderDetail(CartLine cartLine, long orderId) {
        this.orderId = orderId;
        this.bookTitle = cartLine.getBookProduct().getTitle();
        this.bookAuthor = cartLine.getBookProduct().getAuthor();
        this.bookPrice = cartLine.getBookProduct().getPrice();
        this.booksAmount = cartLine.getAmount();
    }


}
