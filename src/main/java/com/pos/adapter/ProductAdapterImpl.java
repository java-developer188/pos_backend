package com.pos.adapter;

import java.util.List;

import com.pos.dto.ProductDto;
import com.pos.entity.Product;

public class ProductAdapterImpl implements BasePosAdapter<ProductDto, Product>  {

	@Override
	public Product convertDtoToDao(ProductDto t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDto convertDaoToDto(Product u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ProductDto convertDaoListToDtoList(List<Product> u) {
		// TODO Auto-generated method stub
		return null;
	}

}
