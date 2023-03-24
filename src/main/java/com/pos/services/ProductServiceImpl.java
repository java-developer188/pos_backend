package com.pos.services;

import com.pos.entity.Product;
import com.pos.exception.NameException;
import com.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements com.pos.services.Service<Product> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    @Override
    public Product add(Product product) {
        if ( null != product.getName() ) {
            if (!product.getName().matches("^[a-zA-Z\\s]+")) {
                throw new NameException("Only alphabets and spaces are allowed for student's name.");
            }
        }
        Product product1 = productRepository.save(product);
        return product1;
    }

    @Override
    public void deleteUsingId(Long id) {

    }

    @Override
    public void deleteUsingName(String name) {

    }

    @Override
    public Product update(Long id, Product product) {
        return null;
    }

    @Override
    public Product patch(Long id, Product dto) {
        return null;
    }
}
