package com.pos.services;

import com.pos.entity.Product;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.ProductOrderRepository;
import com.pos.repository.ProductRepository;
import com.pos.response.ProductDetailsResponse;

import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductPOSServiceImpl implements POSService<Product> {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductOrderRepository productOrderRepo;

    @Override
    public List<Product> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }
    public List<String> findAllProductNames(){
        List<String> productNames= productRepository.findAllNames();
        return productNames;
    }

    public Integer getTotalSoldQuantityOfProductById(Long id){
        Integer totalSold = productOrderRepo.calculateTotalSoldProductById(id);
        return totalSold;
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
            if (!product.getName().matches("^[a-zA-Z0-9\\s]+")) {
                throw new NameException("Only alphabets and spaces are allowed for product's name.");
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
    public void deleteUsingName(String name) throws RecordNotFoundException {
        Optional<Product> productOptional = productRepository.findByName(name);
        if(productOptional.isPresent()){
            productRepository.delete(productOptional.get());
        }
        else {
            throw new RecordNotFoundException("Product not found");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product update(Long id, Product product) {
        Product updateProduct = null;
        Optional<Product> productOptional = productRepository.findById(product.getId());
        Optional<Inventory> inventoryOptional = inventoryR
        if(productOptional.isPresent()){
            updateProduct = productOptional.get();
            updateProduct.setName(product.getName());
            updateProduct.setBatchNum(product.getBatchNum());
            updateProduct.setMfgDate(product.getMfgDate());
            updateProduct.setExpiryDate(product.getExpiryDate());
            updateProduct.setInventory(null);
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
            else if(product.getPrice()!=null){
                patchProduct.setPrice(product.getPrice());
            }
        }
        return patchProduct;
    }
    
    




}
