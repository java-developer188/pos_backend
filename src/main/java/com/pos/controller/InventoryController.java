package com.pos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.entity.CustomerEntity;
import com.pos.entity.InventoryEntity;
import com.pos.exception.NameException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.InventoryRepository;
import com.pos.services.InventoryServiceImpl;
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
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepo;
    @Autowired
    InventoryServiceImpl inventoryService;


    @PostMapping("/inventory/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) /*throws IOException*/ {
        try {
            List<InventoryEntity> inventoryEntityList = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                InventoryEntity inventoryEntity = new InventoryEntity();
                inventoryEntity.setAvailableStock(Long.valueOf(row.getCell(0).getStringCellValue()));
                inventoryEntity.setSoldStock(Long.valueOf(row.getCell(1).getStringCellValue()));
                //customerEntity.setContactInfo(Long.valueOf(row.getCell(2).getStringCellValue()));

                inventoryEntityList.add(inventoryEntity);
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
    public ResponseEntity<List<InventoryEntity>> getInventory() {
        //return new ResponseEntity<>(playerRepo.findAllNames(), HttpStatus.OK);
        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
    }



    @PostMapping("/inventory")
    public ResponseEntity<InventoryEntity> addInventory(@RequestBody InventoryEntity inventoryEntity) {

        try {
            inventoryService.add(inventoryEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NameException nameException) {
            System.out.println(nameException.getMessage());
            return new ResponseEntity<>(inventoryEntity, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<InventoryEntity> updateCustomer(@PathVariable Long id, @RequestBody InventoryEntity inventoryEntity) {
        inventoryService.update(id, inventoryEntity);
        return new ResponseEntity<InventoryEntity>(HttpStatus.OK);
    }

    @PatchMapping("/inventory/{id}")
    public InventoryEntity patchInventory(@PathVariable Long id, @RequestBody InventoryEntity inventoryEntity) {
        return inventoryService.patch(id, inventoryEntity);
    }
    @DeleteMapping("/inventory/id/{id}")
    public ResponseEntity<InventoryEntity> deleteCustomer(@PathVariable Long id) {
        try {
            inventoryService.deleteUsingId(id);
            return new ResponseEntity<InventoryEntity>(HttpStatus.OK);
        } catch (RecordNotFoundException recordNotFoundException) {
            System.out.println(recordNotFoundException.getMessage());
            return new ResponseEntity<InventoryEntity>(HttpStatus.NOT_FOUND);
        }
    }






}
