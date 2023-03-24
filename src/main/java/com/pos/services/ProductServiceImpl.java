package com.pos.services;

import com.pos.entity.Product;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements com.pos.services.Service<Product> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }
    public List<String> findAllProductNames(){
        List<String> productNames= productRepository.findAllNames();
        return productNames;
    }

    public Product findByProductName(String name) throws RecordNotFoundException {

        Optional<Product> productOptional = productRepository.findByName(name);
        Product product = null;
        if(productOptional.isPresent()){
            product = productOptional.get();
        }
        else {
            throw new RecordNotFoundException("Product not found. Try with space between name");
        }
        return product;
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
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            productRepository.delete(productOptional.get());
        }
        else {
            throw new RecordNotFoundException("Product not found");
        }
    }

    @Override
    public void deleteUsingName(String name) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product update(Long id, Product product) {
        Product updateProduct = null;
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if(productOptional.isPresent()){
           updateProduct = productOptional.get();
            updateProduct.setName(product.getName());
            updateProduct.setBatchNum(product.getBatchNum());
            updateProduct.setMfgDate(product.getMfgDate());
            updateProduct.setExpiryDate(product.getExpiryDate());
        }
        return updateProduct;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product patch(Long id, Product product) {
        Product patchProduct = null;
        Optional<Product> productOptional  = productRepository.findById(id);
        if(productOptional.isPresent()){
            patchProduct = productOptional.get();
            if (product.getName() != null) {
                patchProduct.setName(product.getName());
            }
            else if (product.getBatchNum()!= null) {
                patchProduct.setBatchNum(product.getBatchNum());
            }
            else if (product.getMfgDate()!= null) {
                patchProduct.setMfgDate(product.getMfgDate());
            }
            else if (product.getExpiryDate()!= null) {
                patchProduct.setExpiryDate(product.getExpiryDate());
            }
        }
        return patchProduct;
    }




}
