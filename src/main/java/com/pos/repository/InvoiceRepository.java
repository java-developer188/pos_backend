package com.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pos.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	//using positional parameter in the query as ?1 says first parameter ?2 would say second parameter
    @Query(value = "SELECT * FROM invoice WHERE order_id=?1",nativeQuery = true)
    Optional<Invoice> findByOrderId(Long order_id);

    @Query(value = "SELECT invoice_id FROM invoice ORDER BY invoice_id DESC LIMIT 1", nativeQuery = true)
    Long findLastInvoiceNumber();
}
