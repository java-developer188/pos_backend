package com.pos.controller;

import com.pos.entity.Order;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.services.OrderPOSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    
    @Autowired
    OrderPOSServiceImpl orderServiceImpl;
    
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(orderServiceImpl.findAll(), HttpStatus.OK);
    }

    @GetMapping("/order/{name}")
    public ResponseEntity<List<Order>> getOrderByCustomerName(@PathVariable String name) {
        try {
            List<Order> orderList = orderServiceImpl.findByCustomerName(name);
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Order> addOrders(@RequestBody Order order) {

        try {
            orderServiceImpl.add(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(order, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updateOrder = orderServiceImpl.update(id, order);
        return new ResponseEntity<Order>(updateOrder,HttpStatus.OK);
    }

    @PatchMapping("/order/{id}")
    public ResponseEntity<Order> patchOrder(@PathVariable Long id, @RequestBody Order order) {
        Order patchOrder = orderServiceImpl.patch(id, order);
        return new ResponseEntity<Order>(patchOrder,HttpStatus.OK);
    }
    
    @DeleteMapping("/order/id/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        try {
            orderServiceImpl.deleteUsingId(id);
            return new ResponseEntity<Order>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }

}
