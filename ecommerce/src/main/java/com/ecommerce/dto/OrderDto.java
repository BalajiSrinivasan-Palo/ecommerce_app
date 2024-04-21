package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long productId;
    private String uen;
    private int quantity;
    private double totalPrice;
    private Date orderDate;
    private String status;
    private String orderReferenceId;
}
