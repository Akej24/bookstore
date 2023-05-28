package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;

import javax.validation.Valid;

record DeliveryCommand(

        @Valid long orderId,
        @Valid Address address

) { }
