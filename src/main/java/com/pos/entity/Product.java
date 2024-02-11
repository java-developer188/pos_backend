package com.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.entity.compositekey.ProductId;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@IdClass(ProductId.class)
public class Product {

    @Id
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Id
    @Column(name = "batchNum")
    private String batchNum;
    @Column
    private String expiryDate;
    @Column
    private String mfgDate;
    @Column(name = "product_price")
    private Integer price;
    @Transient
    private Integer quantity;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    Set<ProductOrder> productOrders;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    @JsonIgnore
    private Inventory inventory;
    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Set<ProductOrder> getProductOrders() {
        return productOrders;
    }
    public void setProductOrders(Set<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }
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
}
