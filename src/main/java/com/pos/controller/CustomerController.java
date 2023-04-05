package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.dto.CustomerDto;
import com.pos.entity.Customer;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.CustomerRepository;
import com.pos.services.CustomerPOSServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerPOSServiceImpl customerService;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/customer/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) /*throws IOException*/ {
        try {
            List<Customer> customerList = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Customer customer = new Customer();
                customer.setName(row.getCell(0).getStringCellValue());
                customer.setAddress(row.getCell(1).getStringCellValue());
                customer.setContactInfo(Long.valueOf(row.getCell(2).getStringCellValue()));
                customer.setNtn(Long.valueOf(row.getCell(3).getStringCellValue()));
                customerList.add(customer);
            }
            workbook.close();
            customerRepository.saveAll(customerList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(customerList);
            return ResponseEntity.ok(json);
        } catch (IOException ioException) {
            System.out.println("IO Exception");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getCustomers() {
        //return new ResponseEntity<>(playerRepo.findAllNames(), HttpStatus.OK);
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/customer/names")
    public ResponseEntity<List<String>> getCustomerNames() {
        return new ResponseEntity<>(customerService.findAllCustomerNames(), HttpStatus.OK);

    }

    @GetMapping("/customer/{name}")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        try {
            Customer customer = customerService.findByCustomerName(name);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {

        try {
            customerService.add(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(customer, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/customer/order")
    public ResponseEntity<CustomerDto> addCustomerOrder(@RequestBody CustomerDto customer) {

        try {
            customerService.addCustomerWithOrder(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(customer, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customerService.update(id, customer);
        return new ResponseEntity<Customer>(HttpStatus.OK);
    }


    @PatchMapping("/customer/{id}")
    public Customer patchCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.patch(id, customer);
    }

    @DeleteMapping("/customer/id/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteUsingId(id);
            return new ResponseEntity<Customer>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customer/name/{name}")
    public ResponseEntity<Customer> deleteCustomerUsingName(@PathVariable String name) {
        try {
            customerService.deleteUsingName(name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}










