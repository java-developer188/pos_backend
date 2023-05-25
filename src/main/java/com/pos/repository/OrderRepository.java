package com.pos.repository;

import com.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "SELECT * " +
                   "FROM orders o" +
                   " Left Join customers c" +
                   " ON o.customer_id=c.customer_id " +
                   "where c.customer_name =:name"
                    ,nativeQuery = true)   //aik aur tareeka parameter read krne ka
                                           // where c.customer_name=?1   yahan 1 baata raha hai ke first
                                                                       // parameter ke value ai gi yahan
    List<Order> findByCustomerName(String name);

}
