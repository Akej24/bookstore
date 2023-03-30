package com.bookstoreapplication.bookstore.purchase.query;

import com.bookstoreapplication.bookstore.user.SimpleUserQueryDto;
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
    private long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SimpleUserQueryDto user;

}
