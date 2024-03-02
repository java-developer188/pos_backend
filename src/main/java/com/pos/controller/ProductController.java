package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.dto.ProductDto;
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
@CrossOrigin(value = "*")
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
                product.setId((long) row.getCell(2).getNumericCellValue());
//                product.setPrice(Integer.valueOf(row.getCell(2).getStringCellValue()));
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

    @GetMapping("/product/total/{id}")
    public ResponseEntity<Integer> getTotalSoldQuantityOfProduct(@PathVariable Long id) {
        try {
            Integer totalQuantityOfProduct = productService.getTotalSoldQuantityOfProductById(id);
            return new ResponseEntity<>(totalQuantityOfProduct, HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
        Product product = null;
        try {
            product = productService.addProduct(productDto);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            System.out.println(exception.getMessage() + "");
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/product/new/batch")
    public ResponseEntity<Product> addProductWithNewBatchNum(@RequestBody ProductDto productDto) {
        Product product = null;
        try {
            product = productService.addProductWithNewBatch(productDto);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/product/{id}/{batchNum}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @PathVariable String batchNum, @RequestBody Product product) {
        productService.update(id, batchNum, product);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }

    @PatchMapping("/product/{id}/{batchNum}")
    public Product patchProduct(@PathVariable Long id, @PathVariable String batchNum, @RequestBody Product product) {
        return productService.patch(id, batchNum, product);
    }

    @DeleteMapping("/product/id/{id}/{batchNum}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id, @PathVariable String batchNum) {
        try {
            productService.deleteUsingIdAndBatch(id, batchNum);
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