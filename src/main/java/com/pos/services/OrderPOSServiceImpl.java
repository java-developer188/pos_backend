package com.pos.services;

import com.pos.entity.Order;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderPOSServiceImpl implements POSService<Order> {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    @Override
    public Order add(Order order) {
//        Order order1 = null;
//        Customer customer = order.getCustomerEntity();
//        order1.setCustomerEntity(customer);
        return orderRepository.save(order);
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

//    public Order addOrder(Long id, ProductOrder productOrder) {
//
//        ProductOrder productOrder1 = null;
//        Order order =null;
//        productOrder1.setOrder(productOrder.getOrder());
//        Customer customer = order.getCustomerEntity();
//        order1.setCustomerEntity(customer);
//        order1.setProductOrders(order.);
//        return null;
//    }

    @Override
    public Order patch(Long id, Order dto) {
        return null;
    }
}
