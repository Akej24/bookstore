package com.bookstoreapplication.bookstore.purchase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("")
    ResponseEntity<?> createNewPurchase(@RequestBody PurchaseRequest purchaseRequest){
        purchaseService.createNewPurchase(purchaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/payment")
    ResponseEntity<?> payForPurchase(@RequestBody PaymentRequest paymentRequest){
        purchaseService.payForPurchase(paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
