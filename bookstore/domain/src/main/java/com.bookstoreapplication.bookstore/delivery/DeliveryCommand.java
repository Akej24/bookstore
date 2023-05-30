package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;

import javax.validation.Valid;
import java.util.UUID;

record DeliveryCommand(

        @Valid UUID orderId,
        @Valid Address address

) { }
