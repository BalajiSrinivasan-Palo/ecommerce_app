package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    private String productName;

    @NotNull(message = "UEN cannot be null")
    private String uen;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be greater than 0")
    private Integer qty;

    @NotNull(message = "Total price cannot be null")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;
}
