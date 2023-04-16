package com.pos.repository;

import com.pos.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long> {

    @Query(value = "SELECT SUM(o.total_price) FROM product_order o",nativeQuery = true)
    Long getTotalPrice();


}
