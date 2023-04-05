package com.pos.repository;

import com.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "SELECT c.customer_name FROM products c",nativeQuery = true)
    List<String> findAllNames();

    Optional<Customer> findByName(String name);

    Optional<Customer> findByContactInfo(Long contact);

}
