package com.ecommerce.mapper;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "product.shop.name", target = "shop_name")
    @Mapping(source = "id", target = "product_id")
    ProductDto toProductDto(Product product);
    Product toProduct(Product productDto);
}
