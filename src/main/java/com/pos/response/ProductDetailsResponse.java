package com.pos.response;

public class ProductDetailsResponse {
	private Long product_id;
	private String product_name;
	private String batch_no;
	private String product_price;
	private Long sold_stock;
	private Long availale_stock;
	
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public Long getSold_stock() {
		return sold_stock;
	}
	public void setSold_stock(Long sold_stock) {
		this.sold_stock = sold_stock;
	}
	public Long getAvailale_stock() {
		return availale_stock;
	}
	public void setAvailale_stock(Long availale_stock) {
		this.availale_stock = availale_stock;
	}
	
	

}
