package com.pos.repository;

import com.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "SELECT c.customer_name FROM customers c",nativeQuery = true)
    List<String> findAllNames();

    Optional<Customer> findByName(String name);

    Optional<Customer> findByContactInfo(Long contact);

    @Query(value = "Select distinct customer_name from orders as o left join customers as c on o.customer_id=c.customer_id where order_id IN (Select order_id from product_order as po where product_id = :product_id)" , nativeQuery = true)
    List<String> findCustomerNameByProduct(Long product_id);

}
