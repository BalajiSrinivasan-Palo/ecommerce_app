package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    // Getters and setters
    private Long product_id;

    private String shop_name;

    private String name;

    private Integer quantity;

    private Double price;

    private String uen;

}

