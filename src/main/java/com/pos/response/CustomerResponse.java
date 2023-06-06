package com.pos.response;

import com.pos.entity.Customer;

public class CustomerResponse {


    private Customer customer;
    private String message;

    public CustomerResponse(Customer customer, String message) {
        this.customer = customer;
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
