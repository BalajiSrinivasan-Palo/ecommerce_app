package com.ecommerce.controller;

import com.ecommerce.dto.OrderItemDto;
import com.ecommerce.dto.OrderRequestDto;
import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.dto.OrderStatusDto;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.exception.InsufficientStockException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.ecommerce.service.OrderService;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;
    @Test
    void placeOrder() {
        // Arrange

        OrderResponseDto mockResponse = new OrderResponseDto();
        mockResponse.setOrderReferenceId("1213131141414");
        when(orderService.processOrder(any())).thenReturn(mockResponse);

        // Act
        OrderResponseDto response = orderController.placeOrder(getOrderRequestDto()).getBody();

        // Assert
        assertNotNull(response);
        assertEquals("1213131141414", response.getOrderReferenceId());
    }
    @Test
    void placeOrder_insufficientStock() {
        // Arrange
        when(orderService.processOrder(any())).thenThrow(new InsufficientStockException("Insufficient stock"));

        // Act
        ResponseEntity<OrderResponseDto> response = orderController.placeOrder(getOrderRequestDto());

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getOrderReferenceId());
    }

    @Test
    void cancelOrder() {
        // Arrange
        OrderResponseDto mockResponse = new OrderResponseDto();
        mockResponse.setOrderReferenceId("1213131141414");
        when(orderService.processOrder(any())).thenReturn(mockResponse);

        OrderStatusDto mockOrderStatus = new OrderStatusDto();
        mockOrderStatus.setStatus(OrderStatus.CANCELLED);
        when(orderService.getOrderStatus(mockResponse.getOrderReferenceId())).thenReturn(mockOrderStatus);

        // Act
        OrderResponseDto response = orderController.placeOrder(getOrderRequestDto()).getBody();
        orderController.cancelOrder(response.getOrderReferenceId());
        OrderStatusDto orderStatus = orderController.checkOrderStatus(response.getOrderReferenceId()).getBody();

        // Assert
        assertNotNull(response);
        assertEquals("1213131141414", response.getOrderReferenceId());
        assertNotNull(orderStatus);
        assertEquals(OrderStatus.CANCELLED, orderStatus.getStatus());
    }

    @Test
    void checkOrderStatus() {
        //Arrange
        OrderResponseDto mockResponse = new OrderResponseDto();
        mockResponse.setOrderReferenceId("1213131141414");
        when(orderService.processOrder(any())).thenReturn(mockResponse);

        OrderStatusDto mockOrderStatus = new OrderStatusDto();
        mockOrderStatus.setStatus(OrderStatus.SHIPPED);
        when(orderService.getOrderStatus(mockResponse.getOrderReferenceId())).thenReturn(mockOrderStatus);

        // Act
        OrderResponseDto response = orderController.placeOrder(getOrderRequestDto()).getBody();
        OrderStatusDto orderStatus = orderController.checkOrderStatus(response.getOrderReferenceId()).getBody();

        // Assert
        assertNotNull(response);
        assertEquals("1213131141414", response.getOrderReferenceId());
        assertNotNull(orderStatus);
        assertEquals(OrderStatus.SHIPPED, orderStatus.getStatus());
    }

    @Test
    void checkOrderStatus_orderNotFound() {
        // Arrange
        when(orderService.getOrderStatus(anyString())).thenReturn(null);

        // Act
        ResponseEntity<OrderStatusDto> response = orderController.checkOrderStatus("anyOrderReferenceId");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private OrderRequestDto getOrderRequestDto() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        OrderItemDto item = new OrderItemDto();
        item.setProductId(1L);
        item.setQty(2);
        orderRequestDto.setOrders(Collections.singletonList(item));
        return orderRequestDto;
    }
}