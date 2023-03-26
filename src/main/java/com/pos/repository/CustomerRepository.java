package com.pos.repository;

import com.pos.entity.CustomerEntity;
import com.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    @Query(value = "SELECT c.customer_name FROM products c",nativeQuery = true)
    List<String> findAllNames();

    Optional<CustomerEntity> findByName(String name);

}
