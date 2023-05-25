package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.auth.JwtService;
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
@CrossOrigin("http://localhost:3000")
class CheckoutCartController {

    private final CheckoutCartHandler checkoutCartHandler;
    private final JwtService jwtService;

    @PostMapping("")
    ResponseEntity<?> checkout(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        checkoutCartHandler.checkout(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    ResponseEntity<?> getCheckoutCart(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        return new ResponseEntity<>(checkoutCartHandler.getCheckoutCart(customerId), HttpStatus.OK);
    }

    @PatchMapping("/address")
    ResponseEntity<?> updateAddress(@RequestBody Json json, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        checkoutCartHandler.updateAddress(customerId, AddressJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/payment")
    ResponseEntity<?> updatePaymentDetails(@RequestBody Json json, HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        checkoutCartHandler.updatePaymentMethod(customerId, PaymentMethodJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> cancelCheckoutCart(HttpServletRequest request){
        long customerId = jwtService.extractUserIdFromRequest(request);
        checkoutCartHandler.cancelCheckoutCart(customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
