package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductEnquiryDto;
import com.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;



    @InjectMocks
    private ProductController productController;

    @Test
    void getAllMenuItems() {
        when(productService.findAllProductsWithDetails())
                .thenReturn(getProductDtoList());

        ResponseEntity<List<ProductDto>> products = productController.getAllMenuItems();

         assertNotNull(products);
         assertEquals(1, products.getBody().size());
         assertEquals("Pizza", products.getBody().get(0).getName());


    }

    @Test
    void productEnquiry() {
        when(productService.checkProductAvailability(1L))
                .thenReturn(getProductEnquiryDto());

        ResponseEntity<ProductEnquiryDto> enquiryResponse =
                productController.productEnquiry(1L);

         assertNotNull(enquiryResponse);
         assertEquals(10, enquiryResponse.getBody().getQty());
         assertEquals(1,  enquiryResponse.getBody().getProductId());

    }

    private ProductEnquiryDto getProductEnquiryDto() {
        ProductEnquiryDto enquiry = new ProductEnquiryDto();
        enquiry.setProductId(1L);
        enquiry.setQty(10);
        return enquiry;
    }

    private List<ProductDto> getProductDtoList() {
        ProductDto product1 = new ProductDto();
        product1.setProduct_id(1L);
        product1.setName("Pizza");
        product1.setQuantity(10);
        product1.setPrice(100.0);
        return List.of(product1);
         }
}