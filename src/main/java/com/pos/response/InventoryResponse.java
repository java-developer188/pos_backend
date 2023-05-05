package com.pos.response;

import com.pos.entity.Inventory;

public class InventoryResponse {


    private Inventory inventory;
    private String message;

    public InventoryResponse(Inventory inventory, String message) {
        this.inventory = inventory;
        this.message = message;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
