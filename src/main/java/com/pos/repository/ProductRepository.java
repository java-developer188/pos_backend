package com.pos.repository;

import com.pos.entity.Product;
import com.pos.response.ProductDetailsResponse;

import org.hibernate.mapping.Any;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT p.product_name FROM products p",nativeQuery = true)
    List<String> findAllNames();

    Optional<Product> findByName(String name);
    
    

}
