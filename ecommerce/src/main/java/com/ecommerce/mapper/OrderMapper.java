package com.ecommerce.mapper;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);
}
