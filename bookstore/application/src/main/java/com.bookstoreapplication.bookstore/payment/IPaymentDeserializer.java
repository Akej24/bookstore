package com.bookstoreapplication.bookstore.payment;

interface IPaymentDeserializer {

    Payment fromJson(String jsonPayment);

}
