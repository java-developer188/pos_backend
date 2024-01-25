package com.pos.services;

import com.pos.adapter.ProductAdapterImpl;
import com.pos.dto.ProductDto;
import com.pos.entity.Product;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.ProductOrderRepository;
import com.pos.repository.ProductRepository;
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

    @Override
    public Product add(Product product) {
        return null;
    }

    public List<String> findAllProductNames() {
        List<String> productNames = productRepository.findAllNames();
        return productNames;
    }

    public Integer getTotalSoldQuantityOfProductById(Long id) {
        Integer totalSold = productOrderRepo.calculateTotalSoldProductById(id);
        return totalSold;
    }

    public Product findByProductName(String name) throws RecordNotFoundException {

        Optional<Product> productOptional = productRepository.findByName(name);
        Product product = null;
        if (productOptional.isPresent()) {
            product = productOptional.get();
        } else {
            throw new RecordNotFoundException("Product not found. Try with space between name");
        }
        return product;
    }

    public Product addProduct(ProductDto productDto) throws NameException, Exception {
        if (null != productDto.getName()) {
            if (!productDto.getName().matches("^[A-Za-z0-9\\s-]*$")) {
                throw new NameException("Only alphabets and spaces are allowed for product's name.");
            }
        }
        Product product1 = null;
        ProductAdapterImpl productAdapter = new ProductAdapterImpl();
        if (productRepository.findByName(productDto.getName()).isPresent()) {
            if (productRepository.findByBatchNum(productDto.getBatchNum()).isPresent()) {
                throw new Exception("Enter new batch Number");
            }
            Long id = productDto.getId();
            product1 = productAdapter.convertDtoToDaoWithSameId(productDto, id);
        } else {
            product1 = productAdapter.convertDtoToDao(productDto);
        }
        return productRepository.save(product1);
    }

    //@Transactional(rollbackFor = Exception.class)
    public Product addProductWithNewBatch(ProductDto productDto) throws Exception {
        Product product;
        ProductAdapterImpl productAdapter = new ProductAdapterImpl();
        if (null != productDto.getName()) {
            if (!productDto.getName().matches("^[A-Za-z0-9\\s-]*$")) {
                throw new NameException("Only alphabets and spaces are allowed for product's name.");
            }
        }
        Optional<Product> productOptional = productRepository.findByName(productDto.getName());
        if (productOptional.isPresent()) {
            product = productOptional.get();
            product = productAdapter.convertDtoToDaoWithSameId(productDto, product.getId());
        } else {
            throw new Exception("Product does not exist. Add as new product");
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteUsingId(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new RecordNotFoundException("Product not found");
        }
    }

    @Override
    public void deleteUsingName(String name) throws RecordNotFoundException {
        Optional<Product> productOptional = productRepository.findByName(name);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new RecordNotFoundException("Product not found");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product update(Long id, Product product) {
        Product updateProduct = null;
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isPresent()) {
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
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            patchProduct = productOptional.get();
            if (product.getName() != null) {
                patchProduct.setName(product.getName());
            } else if (product.getBatchNum() != null) {
                patchProduct.setBatchNum(product.getBatchNum());
            } else if (product.getMfgDate() != null) {
                patchProduct.setMfgDate(product.getMfgDate());
            } else if (product.getExpiryDate() != null) {
                patchProduct.setExpiryDate(product.getExpiryDate());
            } else if (product.getPrice() != null) {
                patchProduct.setPrice(product.getPrice());
            }
        }
        return patchProduct;
    }


}
