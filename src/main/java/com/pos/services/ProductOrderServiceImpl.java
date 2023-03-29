package com.pos.services;

import com.pos.entity.ProductOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderServiceImpl implements POSService<ProductOrder>{

    @Override
    public List<ProductOrder> findAll() {
        return null;
    }

    @Override
    public ProductOrder add(ProductOrder productOrder) {

        return null;
    }

    @Override
    public void deleteUsingId(Long id) {

    }

    @Override
    public void deleteUsingName(String name) {

    }

    @Override
    public ProductOrder update(Long id, ProductOrder productOrder) {
        return null;
    }

    @Override
    public ProductOrder patch(Long id, ProductOrder dto) {
        return null;
    }
}
