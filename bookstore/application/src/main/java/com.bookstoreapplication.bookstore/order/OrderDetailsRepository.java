package com.bookstoreapplication.bookstore.order;

import java.util.List;

interface OrderDetailsRepository {

    void saveAll(List<OrderDetail> orderDetails);

}
