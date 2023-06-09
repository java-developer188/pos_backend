package com.pos.dto;

import com.pos.entity.Discount;
import com.pos.entity.Product;
import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;

    private Date orderDate;

    private String orderStatus;

    private List<Product> productList;

    private CustomerDto customer;

    private List<Discount> discountList;
    
    private List<InvoiceDto> invoice; 

    public List<InvoiceDto> getInvoice() {
		return invoice;
	}

	public void setInvoice(List<InvoiceDto> invoice) {
		this.invoice = invoice;
	}

	public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
