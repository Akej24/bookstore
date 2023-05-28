package com.bookstoreapplication.bookstore.payment;

import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import com.bookstoreapplication.bookstore.payment.value_object.PaymentStatus;
import com.bookstoreapplication.bookstore.payment.value_object.ServiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "payments")
@Entity
@Getter
@NoArgsConstructor
class Payment {

    @Id
    private UUID paymentId;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Service type cannot be null")
    private ServiceType serviceType;
    private UUID serviceId;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;
    @Embedded
    private TotalPrice totalPrice;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;

    Payment(ServiceType serviceType,
            UUID serviceId,
            PaymentMethod paymentMethod,
            TotalPrice totalPrice
    ) {
        this.paymentId = UUID.randomUUID();
        this.serviceType = serviceType;
        this.serviceId = serviceId;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.paymentStatus = PaymentStatus.INITIALIZED;
    }

    void updateStatus(PaymentStatus paymentStatus){
        this.paymentStatus = PaymentStatus.valueOf(paymentStatus.toString());
    }
}
