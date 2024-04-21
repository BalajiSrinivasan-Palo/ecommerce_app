package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductEnquiryDto;
import com.ecommerce.enums.StockOperation;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductService {

    @Value("${quantity.threshold:0}")
    private int quantityThreshold;

    private final ProductRepository productRepository;

    private final NotificationService notificationService;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.notificationService = notificationService;
    }

    public ProductEnquiryDto checkProductAvailability(Long productId) {
        Product product = findProductById(productId);
        if (product.getQuantity() <= quantityThreshold) {
            notificationService.notifySupplyTeam(ProductEnquiryDto.builder()
                    .productId(product.getId())
                    .qty(product.getQuantity())
                    .build());
        }

        return new ProductEnquiryDto(productId, product.getQuantity());
    }

    @Transactional
    public void updateProductStock(Long productId, int quantity, StockOperation operation) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        if (Objects.requireNonNull(operation) == StockOperation.ADD_STOCK) {
            product.setQuantity(product.getQuantity() + quantity);
        } else if (operation == StockOperation.REMOVE_STOCK) {
            product.setQuantity(product.getQuantity() - quantity);
        }
        productRepository.save(product);
    }

    public List<ProductDto> findAllProductsWithDetails() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toProductDto).toList();
    }

    public Product findProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

}