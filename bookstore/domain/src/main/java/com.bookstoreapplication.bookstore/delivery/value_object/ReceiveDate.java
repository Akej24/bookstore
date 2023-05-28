package com.bookstoreapplication.bookstore.delivery.value_object;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class ReceiveDate implements Serializable {

    private LocalDateTime receiveDate;

}
