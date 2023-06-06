package com.pos.response;

import java.time.LocalDateTime;
import java.util.List;

import com.pos.response.Classes.ProductDescription;

public class InvoiceResponse {
	private Long invoice_id;
	private LocalDateTime invoice_date;
	private LocalDateTime order_Date;
	private String customer_name;
	private Long customer_contactInfo;
	private List<ProductDescription> productDesc; 
	private long order_total_amount;
	private long discounted_amount;
	private long order_deducted_amount;
	
	public Long getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(Long invoice_id) {
		this.invoice_id = invoice_id;
	}
	public LocalDateTime getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(LocalDateTime invoice_date) {
		this.invoice_date = invoice_date;
	}
	public LocalDateTime getOrder_Date() {
		return order_Date;
	}
	public void setOrder_Date(LocalDateTime order_Date) {
		this.order_Date = order_Date;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Long getCustomer_contactInfo() {
		return customer_contactInfo;
	}
	public void setCustomer_contactInfo(Long customer_contactInfo) {
		this.customer_contactInfo = customer_contactInfo;
	}
	
	public long getOrder_total_amount() {
		return order_total_amount;
	}
	public void setOrder_total_amount(long order_total_amount) {
		this.order_total_amount = order_total_amount;
	}
	public long getDiscounted_amount() {
		return discounted_amount;
	}
	public void setDiscounted_amount(long discounted_amount) {
		this.discounted_amount = discounted_amount;
	}
	public List<ProductDescription> getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(List<ProductDescription> productDesc) {
		this.productDesc = productDesc;
	}
	public long getOrder_deducted_amount() {
		return order_deducted_amount;
	}
	public void setOrder_deducted_amount(long order_deducted_amount) {
		this.order_deducted_amount = order_deducted_amount;
	}
	
}
