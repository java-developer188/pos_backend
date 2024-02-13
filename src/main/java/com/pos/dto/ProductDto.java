package com.pos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.entity.Inventory;
import com.pos.entity.ProductOrder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class ProductDto {

    private Long id;

    private String name;

    private String batchNum;

    private String expiryDate;

    private String mfgDate;

    private Integer price;

    private Integer quantity;

    List<ProductOrder> productOrders;

    private InventoryDto inventoryDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(String mfgDate) {
        this.mfgDate = mfgDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public InventoryDto getInventory() {
        return inventoryDto;
    }

    public void setInventory(InventoryDto inventoryDto) {
        this.inventoryDto = inventoryDto;
    }
}
