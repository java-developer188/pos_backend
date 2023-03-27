package com.pos.entity;

import com.pos.enums.OrderStatus;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column
    private  Long quantity;

    @Column
    private Long totalAmount;

    @Column(name=" order_date")
    private Date orderDate;
    @Column(name = "order_status")
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    Set<ProductOrder> productOrders;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private CustomerEntity customerEntity;


    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }
    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(Set<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
