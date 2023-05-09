package com.bookstoreapplication.bookstore.order;

import java.util.List;

interface OrderDetailsRepository {

    <S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

}
