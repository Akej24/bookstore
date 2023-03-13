package com.bookstoreapplication.bookstore.purchase;

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

    @PostMapping("/details")
    ResponseEntity<?> createNewPurchase(@RequestBody PurchaseRequest purchaseRequest){
        purchaseService.createNewPurchase(purchaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
