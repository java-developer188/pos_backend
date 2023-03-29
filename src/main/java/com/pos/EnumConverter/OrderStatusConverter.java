package com.pos.EnumConverter;

import com.pos.enums.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus,String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        return null;
    }
}
