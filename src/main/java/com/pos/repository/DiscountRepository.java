package com.pos.repository;

import com.pos.entity.Discount;
import com.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.net.DatagramSocket;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long>{

   //@Query(value = "SELECT DISTINCT d.discount_percentage FROM discount d WHERE discount_percentage = :discount_percentage",nativeQuery = true)
    Optional<Discount> findByDiscountPercentage(Long discountPercentage);
}
