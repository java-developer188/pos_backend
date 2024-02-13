package com.pos.dto;

import com.pos.entity.Product;

public class InventoryDto {
	private Long id;

    private Double availableStock;

    private Double soldStock;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

//    public void addProduct(Product product){
//        if(productList==null){
//            productList = new ArrayList<>();
//        }
//        productList.add(product);
//
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Double availableStock) {
        this.availableStock = availableStock;
    }

    public Double getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(Double soldStock) {
        this.soldStock = soldStock;
    }

}
