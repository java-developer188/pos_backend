package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.entity.CustomerEntity;
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
            List<CustomerEntity> customerEntityList = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.setName(row.getCell(0).getStringCellValue());
                customerEntity.setAddress(row.getCell(1).getStringCellValue());
                customerEntity.setContactInfo(Long.valueOf(row.getCell(2).getStringCellValue()));
                customerEntity.setNtn(Long.valueOf(row.getCell(3).getStringCellValue()));
                customerEntityList.add(customerEntity);
            }
            workbook.close();
            customerRepository.saveAll(customerEntityList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(customerEntityList);
            return ResponseEntity.ok(json);
        } catch (IOException ioException) {
            System.out.println("IO Exception");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerEntity>> getCustomers() {
        //return new ResponseEntity<>(playerRepo.findAllNames(), HttpStatus.OK);
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/customer/names")
    public ResponseEntity<List<String>> getCustomerNames() {
        return new ResponseEntity<>(customerService.findAllCustomerNames(), HttpStatus.OK);

    }

    @GetMapping("/customer/{name}")
    public ResponseEntity<CustomerEntity> getCustomerByName(@PathVariable String name) {
        try {
            CustomerEntity customerEntity = customerService.findByCustomerName(name);
            return new ResponseEntity<>(customerEntity, HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/customer")
    public ResponseEntity<CustomerEntity> addProducts(@RequestBody CustomerEntity customerEntity) {

        try {
            customerService.add(customerEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(customerEntity, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customerEntity) {
        customerService.update(id, customerEntity);
        return new ResponseEntity<CustomerEntity>(HttpStatus.OK);
    }


    @PatchMapping("/customer/{id}")
    public CustomerEntity patchCustomer(@PathVariable Long id, @RequestBody CustomerEntity customerEntity) {
        return customerService.patch(id, customerEntity);
    }

    @DeleteMapping("/customer/id/{id}")
    public ResponseEntity<CustomerEntity> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteUsingId(id);
            return new ResponseEntity<CustomerEntity>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<CustomerEntity>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customer/name/{name}")
    public ResponseEntity<CustomerEntity> deleteCustomerUsingName(@PathVariable String name) {
        try {
            customerService.deleteUsingName(name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}










