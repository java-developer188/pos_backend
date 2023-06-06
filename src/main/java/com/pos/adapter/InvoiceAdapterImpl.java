package com.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.pos.dto.InvoiceDto;
import com.pos.entity.Customer;
import com.pos.entity.Invoice;
import com.pos.entity.Order;
import com.pos.entity.Product;
import com.pos.entity.ProductOrder;
import com.pos.response.InvoiceResponse;
import com.pos.response.Classes.ProductDescription;

public class InvoiceAdapterImpl implements BasePosAdapter<InvoiceDto, Invoice>{

	@Override
	public Invoice convertDtoToDao(InvoiceDto t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvoiceDto convertDaoToDto(Invoice u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static InvoiceResponse getInvoiceResponse(Order order, Customer customer,List<Product> product, List<ProductOrder> productorder, Invoice invoice ) {
		InvoiceResponse invoiceResp = new InvoiceResponse();
		List<ProductDescription> prodDescriptionList = new ArrayList<>();
		long sumTotalPriceAllProd = 0;
		invoiceResp.setInvoice_id(invoice.getId());
		invoiceResp.setInvoice_date(invoice.getInvoiceDate());
		invoiceResp.setOrder_Date(order.getOrderDate());
		invoiceResp.setCustomer_name(customer.getName());
		invoiceResp.setCustomer_contactInfo(customer.getContactInfo());
		
		//setting product desc in the prod desc (Class) list
		for(int i=0;i<productorder.size();i++) {
			ProductDescription prodDesc = new ProductDescription();
			prodDesc.setName(product.get(i).getName());
			prodDesc.setQuantity(productorder.get(i).getQuantity());
			int unitPrice=productorder.get(i).getPrice()/productorder.get(i).getQuantity();
			prodDesc.setUnitPrice(unitPrice);
			prodDesc.setTotalPrice(productorder.get(i).getPrice());
			sumTotalPriceAllProd= sumTotalPriceAllProd + productorder.get(i).getPrice();
			prodDescriptionList.add(prodDesc);
		}
		invoiceResp.setProductDesc(prodDescriptionList);
		invoiceResp.setOrder_total_amount(sumTotalPriceAllProd);
		long discountedAmount = sumTotalPriceAllProd - order.getTotalAmount();
		invoiceResp.setDiscounted_amount(discountedAmount);
		invoiceResp.setOrder_deducted_amount(order.getTotalAmount());
		
		
		return invoiceResp;
	}


}
