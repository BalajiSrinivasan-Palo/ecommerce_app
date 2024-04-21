package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductEnquiryDto;
import com.ecommerce.service.NotificationService;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Product Controller",description = "Product Controller")
public class ProductController {
    private final NotificationService notificationService;
    private final ProductService productService;

    public ProductController(NotificationService notificationService, ProductService productService) {
        this.notificationService = notificationService;
        this.productService = productService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product enquiry successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @Operation(summary = "Get All menu items")
    @GetMapping("/item_menu")
    public ResponseEntity<List<ProductDto>> getAllMenuItems() {
        List<ProductDto> products = productService.findAllProductsWithDetails();
        return ResponseEntity.ok(products);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product enquiry successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @Operation(summary = "Check product availability")
    @GetMapping("/product_enquiry")
    public ResponseEntity<ProductEnquiryDto> productEnquiry(@NotNull @RequestParam Long productId) {
        ProductEnquiryDto enquiry = productService.checkProductAvailability(productId);
        if (enquiry.getQty() == 0) {
            notificationService.notifySupplyTeam(enquiry);
        }
        return ResponseEntity.ok(enquiry);
    }
}
