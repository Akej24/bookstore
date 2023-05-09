package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.auth.JwtService;
import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/checkoutcart")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class CheckoutController {

    private final CheckoutCartHandler cartHandler;
    private final JwtService jwtService;

    @PatchMapping("/delivery")
    ResponseEntity<?> updateDeliveryDetails(@RequestBody DeliveryDetails deliveryDetails, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.updateDeliveryDetails(customerId, deliveryDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/payment")
    ResponseEntity<?> updatePaymentDetails(@RequestBody PaymentMethod paymentMethod, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.updatePaymentMethod(customerId, paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/order")
    ResponseEntity<?> order(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        cartHandler.order(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
