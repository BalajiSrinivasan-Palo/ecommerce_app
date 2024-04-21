package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
    private String orderReferenceId;
    private List<OrderItemDto> items;
    private OrderStatus status;
}
