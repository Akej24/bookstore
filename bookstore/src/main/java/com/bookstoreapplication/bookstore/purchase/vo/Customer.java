package com.bookstoreapplication.bookstore.purchase.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "users")
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Customer implements Serializable {

        @Id
        private SimpleCustomerId purchaseId;
        private Funds funds;

}
