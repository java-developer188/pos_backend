package com.pos.adapter;

import com.pos.dto.ProductDto;
import com.pos.entity.Inventory;
import com.pos.entity.Product;

public class ProductAdapterImpl implements BasePosAdapter<ProductDto, Product> {
    @Override
    public Product convertDtoToDao(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setBatchNum(productDto.getBatchNum());
        product.setPrice(productDto.getPrice());
        product.setMfgDate(productDto.getMfgDate());
        product.setExpiryDate(productDto.getExpiryDate());
        return product;
    }

    @Override
    public ProductDto convertDaoToDto(Product product) {
        return null;
    }

    public Product convertDtoToDaoWithSameId(ProductDto productDto, Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName(productDto.getName());
        product.setBatchNum(productDto.getBatchNum());
        product.setPrice(productDto.getPrice());
        product.setMfgDate(productDto.getMfgDate());
        product.setExpiryDate(productDto.getExpiryDate());
        product.setInventory(new Inventory());
        return product;
    }

    public Product convertDtoToDaoWithNewBatchNum(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setBatchNum(productDto.getBatchNum());
        product.setPrice(productDto.getPrice());
        product.setMfgDate(productDto.getMfgDate());
        product.setExpiryDate(productDto.getExpiryDate());
        return product;
    }

}
