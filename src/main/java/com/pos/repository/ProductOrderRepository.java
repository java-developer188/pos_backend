package com.pos.repository;

import com.pos.entity.Order;
import com.pos.entity.ProductOrder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {

    @Query(value = "SELECT SUM(o.total_price) FROM product_order o",nativeQuery = true)
    Long getTotalPrice();
    
    Optional<ProductOrder> findByOrder(Order order);
    
    //using positional parameter in the query as ?1 says first parameter ?2 would say second parameter
    @Query(value = "SELECT * FROM product_order o WHERE order_id=?1",nativeQuery = true)
    List<Optional<ProductOrder>> findByOrderId(Long order_id);

    @Query(value = "SELECT SUM(o.product_quantity) FROM product_order o WHERE product_id=?1",nativeQuery = true)
    Integer calculateTotalSoldProductById(Long product_id);

}
