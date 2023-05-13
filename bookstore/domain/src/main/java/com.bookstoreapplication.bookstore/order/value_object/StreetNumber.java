package com.bookstoreapplication.bookstore.order.value_object;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class StreetNumber {

    private Integer streetNumber;

}
