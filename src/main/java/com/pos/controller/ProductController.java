package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.entity.Product;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.ProductRepository;
import com.pos.services.ProductServiceImpl;
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
    ProductServiceImpl productService;

    @Autowired
    ProductRepository productRepo;

    @PostMapping("/product/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) /*throws IOException*/ {
        try{
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
        }
        catch (IOException ioException){
            System.out.println("IO Exception");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        //return new ResponseEntity<>(playerRepo.findAllNames(), HttpStatus.OK);
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    /* @GetMapping("/player/names")
    public ResponseEntity<List<String>> getPlayerNames() {
        return new ResponseEntity<>(productService.findAllPlayerNames(), HttpStatus.OK);

    }
    @GetMapping("/player/{name}")
    public ResponseEntity<Product> getPlayerByName(@PathVariable String name) {
        try{
            Product Product = productService.findByPlayerName(name);
            return new ResponseEntity<>(Product,HttpStatus.OK);
        }
        catch (RecordNotFoundException recordNotFoundException){
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players/team/{teamName}")
    public ResponseEntity<List<Product>> getPlayerByTeamName(@PathVariable String teamName) {
        return new ResponseEntity<>(productService.findByTeamName(teamName), HttpStatus.OK);
    }*/

    @PostMapping("/product")
    public ResponseEntity<Product> addProducts(@RequestBody Product product) {

        try{
            productService.add(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NameException nameException){
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(product,HttpStatus.NOT_ACCEPTABLE);
        }
    }
/*
    @PutMapping("/player/{id}")
    public ResponseEntity<Product> updatePlayer(@PathVariable Long id, @RequestBody Product product)
    {
        productService.update(id,product);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }*/

    /*@PutMapping("/player/details")
    public ResponseEntity<Product> updatePlayerWithDetails(@RequestBody Product product){
        Product updateDto = productService.updatePlayerDetails(product);
        return new ResponseEntity<Product>(updateDto,HttpStatus.OK);
    }

    @PatchMapping("/player/{id}")
    public Product patchPlayer(@PathVariable Long id, @RequestBody Product product) {
        return productService.patch(id,product);
    }

    @DeleteMapping("/player/id/{id}")
    public ResponseEntity<Product> deletePlayer(@PathVariable Long id){
        productService.deleteUsingId(id);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }
    @DeleteMapping("/player/name/{name}")
    public ResponseEntity<Product> deletePlayerUsingName(@PathVariable String name){
        try{
            productService.deleteUsingName(name);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (RecordNotFoundException recordNotFoundException){
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
    }

