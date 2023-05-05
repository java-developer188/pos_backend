package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.entity.Inventory;
import com.pos.entity.Product;
import com.pos.exception.OutOfStockException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.InventoryRepository;
import com.pos.repository.ProductRepository;
import com.pos.response.InventoryResponse;
import com.pos.services.InventoryPOSServiceImpl;
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
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepo;
    @Autowired
    InventoryPOSServiceImpl inventoryService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/inventory/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) /*throws IOException*/ {
        try {
            List<Inventory> inventoryEntityList = new ArrayList<>();


            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Inventory inventory = new Inventory();
               Optional<Product> productOptional = productRepository.findByName(row.getCell(0).getStringCellValue());
               if(productOptional.isPresent()) {
                    Product product = productOptional.get();
                    product.setInventoryEntity(inventory);
                    inventory.setAvailableStock(row.getCell(1).getNumericCellValue());
                   inventoryEntityList.add(inventory);
               }
               }
            workbook.close();
            inventoryRepo.saveAll(inventoryEntityList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(inventoryEntityList);
            return ResponseEntity.ok(json);
        } catch (IOException ioException) {
            System.out.println("IO Exception");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getInventory() {
        //return new ResponseEntity<>(playerRepo.findAllNames(), HttpStatus.OK);
        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/inventory")
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventoryEntity) {
            inventoryService.add(inventoryEntity);
            return new ResponseEntity<Inventory>(HttpStatus.OK);

        }
    @PutMapping("/inventory/{id}")
    public ResponseEntity<Inventory> updateCustomer(@PathVariable Long id, @RequestBody Inventory inventoryEntity) {
        inventoryService.update(id, inventoryEntity);
        return new ResponseEntity<Inventory>(HttpStatus.OK);
    }

    @PatchMapping("/inventory/{id}")
    public ResponseEntity<InventoryResponse> patchInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        String message = "Entered Attribute is patched";
        InventoryResponse response = null;
        Inventory inventory1 = inventoryService.patch(id, inventory);
        response = new InventoryResponse(inventory1,message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/inventory/id/{id}")
    public ResponseEntity<Inventory> deleteCustomer(@PathVariable Long id) {
        try {
            inventoryService.deleteUsingId(id);
            return new ResponseEntity<Inventory>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<Inventory>(HttpStatus.NOT_FOUND);
        }
    }






}
