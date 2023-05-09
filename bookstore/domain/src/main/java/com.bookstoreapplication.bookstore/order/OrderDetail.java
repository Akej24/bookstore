package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.book.value_object.BookAuthor;
import com.bookstoreapplication.bookstore.book.value_object.BookPrice;
import com.bookstoreapplication.bookstore.book.value_object.BookTitle;
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
    private BookTitle bookTitle;
    @Embedded
    private BookAuthor bookAuthor;
    @Embedded
    private BookPrice bookPrice;
    @Embedded
    private BooksAmount booksAmount;

    OrderDetail(CartLine cartLine, long orderId) {
        this.orderId = orderId;
        this.bookId = cartLine.getBookProduct().getBookId();
        this.bookTitle = cartLine.getBookProduct().getBookTitle();
        this.bookAuthor = cartLine.getBookProduct().getBookAuthor();
        this.bookPrice = cartLine.getBookProduct().getBookPrice();
        this.booksAmount = cartLine.getAmount();
    }


}
