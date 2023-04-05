package com.pos.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "batch_No")
    private String batchNum;

    @Column
    private String expiryDate;

    @Column
    private String mfgDate;

    @Column(name = "product_price")
    private Integer price;

    @OneToMany(mappedBy = "product")
    Set<ProductOrder> productOrders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="inventory_id")
    private InventoryEntity inventoryEntity;

    public InventoryEntity getInventoryEntity() {
        return inventoryEntity;
    }

    public void setInventoryEntity(InventoryEntity inventoryEntity) {
        this.inventoryEntity = inventoryEntity;
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
