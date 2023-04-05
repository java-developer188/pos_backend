package com.pos.adapter;

import com.pos.dto.OrderDto;
import com.pos.entity.Order;

public class OrderAdapterImpl implements PosAdapter<OrderDto, Order>{

    @Override
    public Order convertDtoToDao(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderDate(orderDto.getOrderDate());
        return order;
    }

    @Override
    public OrderDto convertDaoToDto(Order order) {
        return null;
    }
}
