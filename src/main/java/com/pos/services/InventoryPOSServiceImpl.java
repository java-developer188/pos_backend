package com.pos.services;

import com.pos.entity.Inventory;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryPOSServiceImpl implements POSService<Inventory> {
    @Autowired
    InventoryRepository inventoryRepo;


    @Override
    public List findAll() {
        List<Inventory> inventoryEntityList= inventoryRepo.findAll();
        return inventoryEntityList;
    }

    @Override
    public Inventory add(Inventory inventoryEntity) {
//        List<Product> productList=new ArrayList<>();
//        inventoryEntity.getProductList().forEach(product -> {
//            productList.add(product);
//        });
//        productList.forEach(product1 ->{
//            product1.setInventoryEntity(inventoryEntity);
//            inventoryEntity.addProduct(product1);
//        });
       return inventoryRepo.save(inventoryEntity);
    }



    @Override
    public void deleteUsingId(Long id) {
        Optional<Inventory> inventoryEntity = inventoryRepo.findById(id);
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
    public Inventory update(Long id, Inventory inventoryEntity) {
        Inventory updateInventory = null;
        Optional<Inventory> inventoryEntity1 = inventoryRepo.findById(inventoryEntity.getId());
        if(inventoryEntity1.isPresent()){
            updateInventory = inventoryEntity1.get();
            updateInventory.setAvailableStock(inventoryEntity.getAvailableStock());
            updateInventory.setSoldStock(inventoryEntity.getSoldStock());
        }
        return updateInventory;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Inventory patch(Long id, Inventory inventory) {
        Inventory patchInventory = null;
        Optional<Inventory> optionalInventory  = inventoryRepo.findById(id);
        if(optionalInventory.isPresent()){
            patchInventory = optionalInventory.get();
            if (inventory.getAvailableStock() != null) {
                patchInventory.setAvailableStock(inventory.getAvailableStock());
            }
            else if (inventory.getSoldStock()!= null) {
                patchInventory.setSoldStock(inventory.getSoldStock());
            }
        }
        return patchInventory;
    }




}
