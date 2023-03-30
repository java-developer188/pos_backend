package com.pos.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventory_id")
    private Long id;

    @Column(name = "available_stock")
    private Long availableStock;

    @Column(name = "sold_stock")
    private Long soldStock;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "inventoryEntity")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProduct(Product product){
        if(productList==null){
            productList = new ArrayList<>();
        }
        productList.add(product);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Long availableStock) {
        this.availableStock = availableStock;
    }

    public Long getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(Long soldStock) {
        this.soldStock = soldStock;
    }

}
