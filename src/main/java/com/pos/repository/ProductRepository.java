package com.pos.repository;

import com.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.product_name FROM products p", nativeQuery = true)
    List<String> findAllNames();
    Optional<Product> findByIdAndBatchNum(Long id, String batchNum);

    //    @Query(value = "SELECT p.product_name FROM products p Where p.product_name = :name LIMIT 1"
//            , nativeQuery = true)
//    Optional<Product> findByName(@Param("name") String name);
    @Query(value = "SELECT p.* FROM products p WHERE p.product_name = :name LIMIT 1", nativeQuery = true)
    Optional<Product> findByName(@Param("name") String name);

    Optional<Product> findByBatchNum(String batchNum);
    
    //Optional<Product> findByBatchNum(String batchNum);
    
    @Query(value = "SELECT coalesce(max(product_id), 0) FROM products", nativeQuery = true)
    public Long getMaxId();

}
