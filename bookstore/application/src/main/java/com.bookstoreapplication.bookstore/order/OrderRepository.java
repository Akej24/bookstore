package com.bookstoreapplication.bookstore.order;

import java.util.List;

interface OrderRepository {

    Order save(Order createdOrder);

    List<Order> findAllByCustomerId(long customerId);

}
