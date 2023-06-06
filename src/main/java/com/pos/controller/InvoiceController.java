package com.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.configuration.ExcelExportService;
import com.pos.entity.Inventory;
import com.pos.entity.Order;
import com.pos.response.InvoiceResponse;
import com.pos.services.InvoiceServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class InvoiceController {
	@Autowired
	InvoiceServiceImpl invoiceService;
	
	@GetMapping("/invoice/create/{id}")
	 public ResponseEntity<InvoiceResponse> createInvoice(@PathVariable Long id) {
		InvoiceResponse invoiceResp = invoiceService.generateInvoice(id);
		if(invoiceResp==null) {
			System.out.println("problem problem problem");
			return new ResponseEntity<>(invoiceResp, HttpStatus.BAD_REQUEST);
		}
		else {
			ExcelExportService.exportInvoice(invoiceResp,id);
			return new ResponseEntity<>(invoiceResp, HttpStatus.OK);
		}
       
 	}
	
	@GetMapping("/invoice/recreate/{id}")
	public ResponseEntity<InvoiceResponse> re_createInvoice(@PathVariable Long id) {
		if(id!=null) {
			System.out.println("Not null");
		}
		else {
			System.out.println("null");
		}
		InvoiceResponse invoiceResp = invoiceService.re_generateInvoice(id);
		if(invoiceResp==null) {
			System.out.println("problem problem problem");
			return new ResponseEntity<>(invoiceResp, HttpStatus.BAD_REQUEST);
		}
		else {
			ExcelExportService.exportInvoice(invoiceResp,id);
			return new ResponseEntity<>(invoiceResp, HttpStatus.OK);
		}

	}
}
	
