package com.pos.services;

import com.pos.entity.CustomerEntity;
import com.pos.entity.Order;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.CustomerRepository;
import com.pos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerPOSServiceImpl implements POSService<CustomerEntity> {

    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    OrderRepository orderRepo;

    @Override
    public List<CustomerEntity> findAll() {
        List<CustomerEntity> customerEntityList= customerRepo.findAll();
        return customerEntityList;
    }

    public List<String> findAllCustomerNames(){
        List<String> customerNames= customerRepo.findAllNames();
        return customerNames;
    }

    public CustomerEntity findByCustomerName(String name) throws RecordNotFoundException {

        Optional<CustomerEntity> customerEntity = customerRepo.findByName(name);
        CustomerEntity customerEntity1 = null;
        if(customerEntity.isPresent()){
            customerEntity1 = customerEntity.get();
        }
        else {
            throw new RecordNotFoundException("Customer not found. Try with space between name");
        }
        return customerEntity1;
    }


    @Override
    public CustomerEntity add(CustomerEntity customerEntity) {
        if ( null != customerEntity.getName() ) {
            if (!customerEntity.getName().matches("^[a-zA-Z\\s]+")) {
                throw new NameException("Only alphabets and spaces are allowed for customer's name.");
            }
        }
        List<Order> orderList = new ArrayList<>();
        customerEntity.getOrderList().forEach(order1 ->{
            orderList.add(order1);
        });
        orderList.forEach(order -> {
            order.setCustomerEntity(customerEntity);
            customerEntity.addOrder(order);
        });
        return customerRepo.save(customerEntity);
    }


    @Override
    public void deleteUsingId(Long id) {
        Optional<CustomerEntity> customerEntity = customerRepo.findById(id);
        if(customerEntity.isPresent()){
            customerRepo.delete(customerEntity.get());
        }
        else {
            throw new RecordNotFoundException("Customer not found");
        }

    }

    @Override
    public void deleteUsingName(String name) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerEntity update(Long id, CustomerEntity customerEntity) {

        CustomerEntity updateCustomer = null;
        Optional<CustomerEntity> customerEntity1 = customerRepo.findById(customerEntity.getId());
        if(customerEntity1.isPresent()){
            updateCustomer = customerEntity1.get();
            updateCustomer.setName(customerEntity.getName());
            updateCustomer.setAddress(customerEntity.getAddress());
            updateCustomer.setContactInfo(customerEntity.getContactInfo());
            updateCustomer.setNtn(customerEntity.getNtn());
        }
        return updateCustomer;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerEntity patch(Long id, CustomerEntity customerEntity) {

        CustomerEntity patchCustomer = null;
        Optional<CustomerEntity> customerEntity1  = customerRepo.findById(id);
        if(customerEntity1.isPresent()){
            patchCustomer = customerEntity1.get();
            if (customerEntity.getName() != null) {
                patchCustomer.setName(customerEntity.getName());
            }
            else if (customerEntity.getAddress()!= null) {
                patchCustomer.setAddress(customerEntity.getAddress());
            }
            else if (customerEntity.getContactInfo()!= null) {
                patchCustomer.setContactInfo(customerEntity.getContactInfo());
            }
            else if (customerEntity.getNtn()!= null) {
                patchCustomer.setNtn(customerEntity.getNtn());
            }
        }
        return patchCustomer;
    }


}
