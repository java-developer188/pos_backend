package com.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.dto.OrderDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_address")
    private String address;

    @Column
    private Long contactInfo;

    @Column(name = "customer_NTN")
    private Long ntn;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    @JsonIgnore
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void addOrder(Order order){
        if(orderList==null){
            orderList = new ArrayList<>();
        }
        orderList.add(order);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Long contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getNtn() {
        return ntn;
    }

    public void setNtn(Long ntn) {
        this.ntn = ntn;
    }
}
