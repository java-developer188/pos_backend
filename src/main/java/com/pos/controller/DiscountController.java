package com.pos.controller;

import com.pos.entity.Customer;
import com.pos.entity.Discount;
import com.pos.exception.NameException;
import com.pos.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountController {

     @Autowired
     DiscountRepository discountRepository;

    @PostMapping("/discount")
    public ResponseEntity<Discount> addDiscount(@RequestBody Discount discount) {

        try {
            discountRepository.save(discount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(discount, HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
