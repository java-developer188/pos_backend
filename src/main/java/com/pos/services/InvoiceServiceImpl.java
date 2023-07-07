package com.pos.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pos.adapter.InvoiceAdapterImpl;
import com.pos.entity.Customer;
import com.pos.entity.Invoice;
import com.pos.entity.Order;
import com.pos.entity.Product;
import com.pos.entity.ProductOrder;
import com.pos.enums.InvoiceStatus;
import com.pos.repository.CustomerRepository;
import com.pos.repository.InvoiceRepository;
import com.pos.repository.OrderRepository;
import com.pos.repository.ProductOrderRepository;
import com.pos.repository.ProductRepository;
import com.pos.response.InvoiceResponse;

@Service
public class InvoiceServiceImpl implements POSService<Invoice> {

	@Autowired
	InvoiceRepository invoiceRepo;
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	ProductOrderRepository productOrderRepo;
	@Autowired
	ProductRepository productRepo;
	
	@Override
	public List<Invoice> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice add(Invoice t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUsingId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUsingName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Invoice update(Long id, Invoice t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice patch(Long id, Invoice dto) {
		// TODO Auto-generated method stub
		return null;
	}
	public Long generateInvoiceNumber() {
		Long lastInvoiceNumber = invoiceRepo.findLastInvoiceNumber();
		Long newInvoiceNumber = 0l;

		if (lastInvoiceNumber == null) {
			// If no invoices exist yet, start with a default value
			newInvoiceNumber = 1l;
		}
		else {
			newInvoiceNumber  = lastInvoiceNumber + 1l;
		}
		return newInvoiceNumber;
	}
	public InvoiceResponse generateInvoice(Long order_id) {
		Boolean isError = false;
		//-------
		List<Object> list = prerequisiteForInvoiceResponse(order_id,isError);
		Order order = (Order)list.get(0);
		Customer customer = (Customer)list.get(1);
		List<Product> product = (List<Product>)list.get(2);
		List<ProductOrder> productOrder = (List<ProductOrder>)list.get(3);
	
		//-----------
		if(!isError) {
			System.out.println("isError is not false");
			Optional<Invoice> OptInvoice = invoiceRepo.findByOrderId(order_id);
			if(OptInvoice.isPresent()) {
				Invoice invoice = OptInvoice.get();
				invoice.setInvoiceDate(LocalDateTime.now());
				invoiceRepo.save(invoice);
				return InvoiceAdapterImpl.getInvoiceResponse(order, customer, product, productOrder,invoice);
			}
			Long currInvoiceNum = generateInvoiceNumber();
			Invoice invoice = new Invoice();
			invoice.setInvoiceDate(LocalDateTime.now()); //every time an invoice is generated/regenerated the current date and time will be inserted in db
			invoice.setInvoiceStatus(InvoiceStatus.GENERATED);
			invoice.setOrder(order);
			invoice.setId(currInvoiceNum);
			invoice = invoiceRepo.save(invoice);
			order.setStatus("COMPLETED");
			order = orderRepo.save(order);
			return InvoiceAdapterImpl.getInvoiceResponse(order, customer, product, productOrder,invoice);
		}
		return null;
	}
	
	
	public InvoiceResponse re_generateInvoice(Long order_id) {
		Boolean isError = false;
		
		List<Object> list = prerequisiteForInvoiceResponse(order_id,isError);
		Order order = (Order)list.get(0);
		Customer customer = (Customer)list.get(1);
		List<Product> product = (List<Product>)list.get(2);
		List<ProductOrder> productOrder = (List<ProductOrder>)list.get(3);
		
		if(!isError) {
			Optional<Invoice> OptInvoice = invoiceRepo.findByOrderId(order_id);
			if(OptInvoice.isPresent()) {
				Invoice invoice = OptInvoice.get();
				invoice.setInvoiceDate(LocalDateTime.now());
				invoiceRepo.save(invoice);
				return InvoiceAdapterImpl.getInvoiceResponse(order, customer, product, productOrder,invoice);
			}
		}
		return null;
	}
	
	public List<Object> prerequisiteForInvoiceResponse(Long order_id, boolean isError){
		//finding orderId in order table
		Optional<Order> orderOpt = orderRepo.findById(order_id);
		Order order = null;
		Customer customer = null;
		List<ProductOrder> productOrder = new ArrayList<>();
		List<Product> product = new ArrayList<>();
		if(orderOpt != null) {
			order = orderOpt.get();
			//----for customer----
			Long customerId = order.getCustomer().getId();
			Optional<Customer> customerOpt = customerRepo.findById(customerId);
			if(customerOpt != null) {
				customer = customerOpt.get();
			}
			else {
				//customer id not found
				isError = true;
			}
			//----for product order----
			List<Optional<ProductOrder>> productOrderOpt = productOrderRepo.findByOrderId(order_id);
			if(productOrderOpt!=null) {
				for(Optional<ProductOrder> productOrd : productOrderOpt) {
					productOrder.add(productOrd.get());
		            //----for product----
		            Long productId = productOrd.get().getProduct().getId();
		            Optional<Product> productOpt = productRepo.findById(productId);
		            if(productOpt != null) {
		            	product.add(productOpt.get());
		            }
		            else {
		            	//no such product found
		            	isError= true;
		            }
				}
			}
			else {
				//no such product order found
				isError = true;
			}
		}
		else {
			//order id not found
			isError = true;
		}
		
		List<Object> list = new ArrayList<>();
		Collections.addAll(list, order,customer,product,productOrder);
		return list;
	}
	

}
