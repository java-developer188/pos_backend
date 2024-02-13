package com.pos.adapter;

import com.pos.dto.InventoryDto;
import com.pos.entity.Inventory;

public class InventoryAdapterImpl implements BasePosAdapter<InventoryDto, Inventory> {

	@Override
	public Inventory convertDtoToDao(InventoryDto t) {
		Inventory inventoryDao = new Inventory();
		inventoryDao.setAvailableStock(t.getAvailableStock());
		inventoryDao.setSoldStock(t.getSoldStock());
		return inventoryDao;
	}

	@Override
	public InventoryDto convertDaoToDto(Inventory u) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
