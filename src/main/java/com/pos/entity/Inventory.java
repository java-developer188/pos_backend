package com.pos.entity;

import javax.persistence.*;

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

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "inventory")
    //@JsonIgnore
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
