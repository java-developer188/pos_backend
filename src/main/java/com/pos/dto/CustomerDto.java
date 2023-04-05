package com.pos.dto;

import com.pos.entity.Order;

import java.util.List;

public class CustomerDto {

    private Long id;

    private String name;

    private Long contactInfo;

    private String address;

    private List<OrderDto> orderDtoList;

    public List<OrderDto> getOrderDtoList() {
        return orderDtoList;
    }

    public void setOrderDtoList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
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

    public Long getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Long contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
