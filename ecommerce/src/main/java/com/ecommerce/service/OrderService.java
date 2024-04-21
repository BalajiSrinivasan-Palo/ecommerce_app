package com.ecommerce.service;

import com.ecommerce.dto.OrderItemDto;
import com.ecommerce.dto.OrderRequestDto;
import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.dto.OrderStatusDto;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.StockOperation;
import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public OrderResponseDto processOrder(OrderRequestDto orderRequest) {
        Order order = new Order();
        for (OrderItemDto item : orderRequest.getOrders()) {
            Product product =  productService.findProductById(item.getProductId());
            if (product.getQuantity() < item.getQty()) {
                throw new InsufficientStockException("Insufficient stock for product ID " + item.getProductId());
            }
            productService.updateProductStock(item.getProductId(),item.getQty(), StockOperation.REMOVE_STOCK);
            OrderItem orderItem = new OrderItem(null,product, item.getQty(), order,item.getTotalPrice());
            order.getItems().add(orderItem);
        }
        order.setOrderReferenceId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PLACED);
        order.setSubmittedAt(new Timestamp(System.currentTimeMillis()));
        Order updatedOrder = orderRepository.save(order);
        return new OrderResponseDto(updatedOrder.getOrderReferenceId());
    }

    @Transactional
    public void cancelOrder(String orderReferenceId) {
        Order order = orderRepository.findByOrderReferenceId(orderReferenceId)
                .orElseThrow(() -> new RuntimeException("Order not found with reference ID: " + orderReferenceId));

        for (OrderItem item : order.getItems()) {
            productService.updateProductStock(item.getProduct().getId(),item.getQuantity(),
                    StockOperation.ADD_STOCK);
        }
        order.setCanceledAt(new Timestamp(System.currentTimeMillis()));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public OrderStatusDto getOrderStatus(String orderReferenceId) {
        Order order = orderRepository.findByOrderReferenceId(orderReferenceId)
                .orElseThrow(() -> new RuntimeException("Order not found with reference ID: " + orderReferenceId));

        return new OrderStatusDto(
                order.getOrderReferenceId(),
                order.getItems().stream()
                        .map(item -> new OrderItemDto(item.getProduct().getId(),item.getProduct().getName() ,item.getProduct().getUen(),
                                item.getQuantity(), item.getTotalPrice()))
                        .toList(),
                order.getStatus()
        );
    }
}
