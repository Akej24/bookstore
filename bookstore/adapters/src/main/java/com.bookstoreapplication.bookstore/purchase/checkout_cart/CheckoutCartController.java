package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.auth.core.JwtFacade;
import dev.mccue.json.Json;
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
class CheckoutCartController {

    private final CheckoutCartHandler checkoutCartHandler;
    private final JwtFacade jwtFacade;

    @PostMapping("")
    ResponseEntity<?> checkout(HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        checkoutCartHandler.checkout(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    ResponseEntity<CheckoutCartJsonQueryResponse> getCheckoutCart(HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        var jsonCheckoutCart = CheckoutCartJsonQueryResponse.from(checkoutCartHandler.getCheckoutCart(customerId));
        return new ResponseEntity<>(jsonCheckoutCart, HttpStatus.OK);
    }

    @PatchMapping("/address")
    ResponseEntity<?> updateAddress(@RequestBody Json json, HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        checkoutCartHandler.updateAddress(customerId, AddressJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/payment")
    ResponseEntity<?> updatePaymentDetails(@RequestBody Json json, HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        checkoutCartHandler.updatePaymentMethod(customerId, PaymentMethodJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> cancelCheckoutCart(HttpServletRequest request){
        long customerId = jwtFacade.extractUserId(request);
        checkoutCartHandler.cancelCheckoutCart(customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
