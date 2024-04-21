package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductEnquiryDto;
import com.ecommerce.enums.StockOperation;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ProductMapper productMapper;

    @Test
    void checkProductAvailability() {
        Long productId = 1L;
        Product product = new Product();
        product.setQuantity(10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductEnquiryDto result = productService.checkProductAvailability(productId);

        assertEquals(productId, result.getProductId());
        assertEquals(product.getQuantity(), result.getQty());
    }

    @Test
    void updateProductStock() {
        Long productId = 1L;
        Product product = new Product();
        product.setQuantity(10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.updateProductStock(productId, 5, StockOperation.ADD_STOCK);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void findAllProductsWithDetails() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        ProductDto productDto1 = new ProductDto();
        ProductDto productDto2 = new ProductDto();
        when(productMapper.toProductDto(product1)).thenReturn(productDto1);
        when(productMapper.toProductDto(product2)).thenReturn(productDto2);

        var result = productService.findAllProductsWithDetails();

        assertEquals(2, result.size());
    }

    @Test
    void checkProductAvailabilityWithThreshold() {
        Long productId = 1L;
        Product product = new Product();
        product.setQuantity(0); // set quantity less than threshold
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.checkProductAvailability(productId);

        verify(notificationService, times(1)).notifySupplyTeam(any(ProductEnquiryDto.class));
    }

    @Test
    void updateProductStockWithRemoveOperation() {
        Long productId = 1L;
        Product product = new Product();
        product.setQuantity(10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.updateProductStock(productId, 5, StockOperation.REMOVE_STOCK);

        assertEquals(5, product.getQuantity());
    }
}