package com.bookstoreapplication.bookstore.purchase.query;

import com.bookstoreapplication.bookstore.user.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "purchases")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SimplePurchaseQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
