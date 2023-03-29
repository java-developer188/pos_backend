package com.pos.controller;

import com.pos.entity.ProductOrder;
import com.pos.services.ProductOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductOrderController {

    @Autowired
    ProductOrderServiceImpl productOrderService;

   @PostMapping("/order//new{id}")
    public ResponseEntity<ProductOrder> addOrderWithProducts(@PathVariable Long id, @RequestBody ProductOrder productOrder) {
        ProductOrder addedOrder = productOrderService.add(productOrder);
        return new ResponseEntity<ProductOrder>(addedOrder, HttpStatus.OK);
    }



}
