package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.SimpleCustomerId;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/purchases")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class PurchaseController {

    private final PurchaseCommandHandler purchaseCommandHandler;

    @PostMapping("")
    ResponseEntity<?> createNewPurchase(@RequestBody @Valid PurchaseCommand purchaseCommand){
        purchaseCommandHandler.placeOrder(purchaseCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/payment")
    ResponseEntity<?> payForPurchase(@RequestBody @Valid SimpleCustomerId userIdRequest){
        purchaseCommandHandler.payForPurchase(userIdRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @DeleteMapping("")
//    ResponseEntity<?> cancelPurchase(@RequestBody SimpleCustomerId userIdRequest){
//        purchaseCommandHandler.cancelPurchase(userIdRequest.customerId());
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//    @GetMapping("")
//    ResponseEntity<List<Purchase>> getAllPurchases(@RequestBody SimpleCustomerId userIdRequest){
//        List<Purchase> purchases = purchaseCommandHandler.getAllPurchases(userIdRequest.customerId());
//        return ResponseEntity.status(HttpStatus.OK).body(purchases);
//    }
//
//    @GetMapping("/details")
//    ResponseEntity<?> getAllPurchasesWithDetails(@RequestBody SimpleCustomerId userIdRequest){
//        List<Purchase> purchases = purchaseCommandHandler.getAllPurchasesWithDetails(userIdRequest.customerId());
//        return ResponseEntity.status(HttpStatus.OK).body(purchases);
//    }

}
