package com.bookstoreapplication.bookstore.purchase;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ResponseEntity<?> payForPurchase(@RequestBody UserIdRequest userIdRequest){
        purchaseService.payForPurchase(userIdRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> cancelPurchase(@RequestBody UserIdRequest userIdRequest){
        purchaseService.cancelPurchase(userIdRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("")
    ResponseEntity<List<PurchaseResponse>> getAllPurchases(@RequestBody UserIdRequest userIdRequest){
        List<PurchaseResponse> purchases = purchaseService.getAllPurchases(userIdRequest);
        return ResponseEntity.status(HttpStatus.OK).body(purchases);
    }

    @GetMapping("/details")
    ResponseEntity<?> getAllPurchasesWithDetails(@RequestBody UserIdRequest userIdRequest){
        List<PurchaseResponse> purchases = purchaseService.getAllPurchasesWithDetails(userIdRequest);
        return ResponseEntity.status(HttpStatus.OK).body(purchases);
    }

}
