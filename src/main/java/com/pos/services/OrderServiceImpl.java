package com.pos.services;

import com.pos.entity.Order;
import com.pos.entity.Product;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements Service<Order>{

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    @Override
    public Order add(Order order) {
        Order order1 = orderRepository.save(order);
        return order1;
    }

    @Override
    public void deleteUsingId(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            orderRepository.delete(orderOptional.get());
        }
        else {
            throw new RecordNotFoundException("Order not found");
        }
    }

    @Override
    public void deleteUsingName(String name) {

    }

    @Override
    public Order update(Long id, Order order) {
        return null;
    }

    @Override
    public Order patch(Long id, Order dto) {
        return null;
    }
}
