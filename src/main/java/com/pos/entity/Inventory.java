package com.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventory_id")
    private Long id;

    @Column(name = "available_stock")
    private Double availableStock;

    @Column(name = "sold_stock")
    private Double soldStock;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "inventoryEntity")
    @JsonIgnore
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
