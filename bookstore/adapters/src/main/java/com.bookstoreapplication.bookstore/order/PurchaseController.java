package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class PurchaseController {

    private final CartHandler cartHandler;

//    @PostMapping("")
//    ResponseEntity<?> createNewPurchase(@RequestBody @Valid PurchaseCommand purchaseCommand){
//        purchaseCommandHandler.placeOrder(purchaseCommand);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @PostMapping("/payment")
//    ResponseEntity<?> payForPurchase(@RequestBody @Valid SimpleCustomerId userIdRequest){
//        purchaseCommandHandler.payForPurchase(userIdRequest);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

//    @DeleteMapping("")
//    ResponseEntity<?> cancelPurchase(@RequestBody SimpleCustomerId userIdRequest){
//        purchaseCommandHandler.cancelPurchase(userIdRequest.customerId());
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//    @GetMapping("")
//    ResponseEntity<List<Order>> getAllPurchases(@RequestBody SimpleCustomerId userIdRequest){
//        List<Order> purchases = purchaseCommandHandler.getAllPurchases(userIdRequest.customerId());
//        return ResponseEntity.status(HttpStatus.OK).body(purchases);
//    }
//
//    @GetMapping("/details")
//    ResponseEntity<?> getAllPurchasesWithDetails(@RequestBody SimpleCustomerId userIdRequest){
//        List<Order> purchases = purchaseCommandHandler.getAllPurchasesWithDetails(userIdRequest.customerId());
//        return ResponseEntity.status(HttpStatus.OK).body(purchases);
//    }

}
