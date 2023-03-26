package com.pos.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class CustomerEntity {

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

    @OneToMany(mappedBy = "customerEntity")
    private List<Order> orderList;


    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
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
