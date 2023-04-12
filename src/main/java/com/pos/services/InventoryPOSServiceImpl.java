package com.pos.services;

import com.pos.entity.InventoryEntity;
import com.pos.entity.Product;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryPOSServiceImpl implements POSService<InventoryEntity> {
    @Autowired
    InventoryRepository inventoryRepo;


    @Override
    public List findAll() {
        List<InventoryEntity> inventoryEntityList= inventoryRepo.findAll();
        return inventoryEntityList;
    }

    @Override
    public InventoryEntity add(InventoryEntity inventoryEntity) {
        List<Product> productList=new ArrayList<>();
        inventoryEntity.getProductList().forEach(product -> {
            productList.add(product);
        });
        productList.forEach(product1 ->{
            product1.setInventoryEntity(inventoryEntity);
            inventoryEntity.addProduct(product1);
        });
        return inventoryRepo.save(inventoryEntity);
    }



    @Override
    public void deleteUsingId(Long id) {
        Optional<InventoryEntity> inventoryEntity = inventoryRepo.findById(id);
        if(inventoryEntity.isPresent()){
            inventoryRepo.delete(inventoryEntity.get());
        }
        else {
            throw new RecordNotFoundException("Inventory not found");
        }

    }

    @Override
    public void deleteUsingName(String name) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryEntity update(Long id, InventoryEntity inventoryEntity) {
        InventoryEntity updateInventory = null;
        Optional<InventoryEntity> inventoryEntity1 = inventoryRepo.findById(inventoryEntity.getId());
        if(inventoryEntity1.isPresent()){
            updateInventory = inventoryEntity1.get();
            updateInventory.setAvailableStock(inventoryEntity.getAvailableStock());
            updateInventory.setSoldStock(inventoryEntity.getSoldStock());
            //updateInventory.setContactInfo(inventoryEntity.getContactInfo());

        }
        return updateInventory;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryEntity patch(Long id, InventoryEntity inventoryEntity) {
        InventoryEntity patchInventory = null;
        Optional<InventoryEntity> inventoryEntity1  = inventoryRepo.findById(id);
        if(inventoryEntity1.isPresent()){
            patchInventory = inventoryEntity1.get();
            if (inventoryEntity.getAvailableStock() != null) {
                patchInventory.setAvailableStock(inventoryEntity.getAvailableStock());
            }
            else if (inventoryEntity.getSoldStock()!= null) {
                patchInventory.setSoldStock(inventoryEntity.getSoldStock());
            }
//            else if (inventoryEntity.inventoryEntity()!= null) {
//                patchCustomer.setContactInfo(inventoryEntity.getContactInfo());
//            }
//            else if (inventoryEntity.getNtn()!= null) {
//                patchCustomer.setNtn(inventoryEntity.getNtn());
//            }
        }
        return patchInventory;
    }




}