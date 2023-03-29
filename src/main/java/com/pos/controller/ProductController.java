package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.entity.Product;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.ProductRepository;
import com.pos.services.ProductPOSServiceImpl;
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
public class ProductController {

    @Autowired
    ProductPOSServiceImpl productService;

    @Autowired
    ProductRepository productRepo;

    @PostMapping("/product/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) /*throws IOException*/ {
        try {
            List<Product> productList = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Product product = new Product();
                product.setName(row.getCell(0).getStringCellValue());
                product.setBatchNum(row.getCell(1).getStringCellValue());
//                product.setMfgDate(row.getCell(2).getStringCellValue());
//                product.setExpiryDate(row.getCell(3).getStringCellValue());
                productList.add(product);
            }
            workbook.close();
            productRepo.saveAll(productList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(productList);
            return ResponseEntity.ok(json);
        } catch (IOException ioException) {
            System.out.println("IO Exception");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        //return new ResponseEntity<>(playerRepo.findAllNames(), HttpStatus.OK);
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/names")
    public ResponseEntity<List<String>> getProductNames() {
        return new ResponseEntity<>(productService.findAllProductNames(), HttpStatus.OK);

    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        try {
            Product Product = productService.findByProductName(name);
            return new ResponseEntity<>(Product, HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProducts(@RequestBody Product product) {

        try {
            productService.add(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productService.update(id, product);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }

    @PatchMapping("/product/{id}")
    public Product patchProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.patch(id, product);
    }

    @DeleteMapping("/product/id/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteUsingId(id);
            return new ResponseEntity<Product>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/name/{name}")
    public ResponseEntity<Product> deleteProductUsingName(@PathVariable String name) {
        try {
            productService.deleteUsingName(name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

