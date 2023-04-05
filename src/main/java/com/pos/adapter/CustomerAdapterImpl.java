package com.pos.adapter;

import com.pos.dto.CustomerDto;
import com.pos.entity.Customer;

public class CustomerAdapterImpl implements PosAdapter<CustomerDto, Customer>{

    @Override
    public Customer convertDtoToDao(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAddress(customerDto.getAddress());
        customer.setContactInfo(customerDto.getContactInfo());
        return customer;
    }

    @Override
    public CustomerDto convertDaoToDto(Customer customer) {
        return null;
    }
}
