package com.pos.adapter;

import com.pos.dto.ProductDto;
import com.pos.entity.Inventory;
import com.pos.entity.Product;

public class ProductAdapterImpl implements BasePosAdapter<ProductDto, Product> {
	InventoryAdapterImpl inventoryAdapter = new InventoryAdapterImpl();
    
    public Product convertDtoToDao(ProductDto productDto, Long prod_max_id) {
        Product product = new Product();
        product.setId(prod_max_id+1);
        product.setName(productDto.getName());
        product.setBatchNum(productDto.getBatchNum());
        product.setPrice(productDto.getPrice());
        product.setMfgDate(productDto.getMfgDate());
        product.setExpiryDate(productDto.getExpiryDate());
        product.setInventory(inventoryAdapter.convertDtoToDao(productDto.getInventory()));
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
        product.setInventory(inventoryAdapter.convertDtoToDao(productDto.getInventory()));
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

	@Override
	public Product convertDtoToDao(ProductDto t) {
		// TODO Auto-generated method stub
		return null;
	}
}