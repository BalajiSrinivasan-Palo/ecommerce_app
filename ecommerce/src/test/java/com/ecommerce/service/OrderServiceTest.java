package com.ecommerce.service;

import com.ecommerce.dto.OrderItemDto;
import com.ecommerce.dto.OrderRequestDto;
import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.dto.OrderStatusDto;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.Shop;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @BeforeEach
    void Setup()
    {
        when(productService.findProductById(any())).thenReturn(getProduct());
        when(orderRepository.save(any())).thenReturn(getOrder());
    }

    @Test
    void processOrder() {
        Order order=getOrder();
        doNothing().when(productService).updateProductStock(any(),anyInt(),any());
        OrderResponseDto response = orderService.processOrder(getOrderRequestDto());
        assertNotNull(response);
        assertEquals(order.getOrderReferenceId(), response.getOrderReferenceId());

    }

    @Test
    void cancelOrder() {
        when(orderRepository.findByOrderReferenceId(anyString())).thenReturn(Optional.of(getOrder()));
        OrderResponseDto response = orderService.processOrder(getOrderRequestDto());
        when(orderRepository.save(any())).thenReturn(getOrder());
        assertNotNull(response);
        assertEquals("456789820", response.getOrderReferenceId());
        orderService.cancelOrder(response.getOrderReferenceId());
        OrderStatusDto orderStatus = orderService.getOrderStatus(response.getOrderReferenceId());
        assertNotNull(orderStatus);
        assertEquals(OrderStatus.CANCELLED, orderStatus.getStatus());

    }

    @Test
    void getOrderStatus() {
        when(orderRepository.findByOrderReferenceId(anyString())).thenReturn(Optional.of(getOrder()));
        OrderResponseDto response = orderService.processOrder(getOrderRequestDto());
        assertNotNull(response);
        assertEquals("456789820", response.getOrderReferenceId());
        OrderStatusDto orderStatus = orderService.getOrderStatus(response.getOrderReferenceId());
        assertNotNull(orderStatus);
        assertEquals(OrderStatus.PLACED, orderStatus.getStatus());
    }

    private OrderRequestDto getOrderRequestDto() {
        OrderRequestDto orderRequest = new OrderRequestDto();
        OrderItemDto item = new OrderItemDto();
        item.setProductId(1L);
        item.setQty(1);
        orderRequest.setOrders(List.of(item));
        return orderRequest;
    }

    private Product getProduct() {

        Shop shop=new Shop();
        shop.setName("Lazada");
        shop.setId(1L);
        Product product=new Product();
        product.setQuantity(1);
        product.setId(2L);
        product.setName("name");
        product.setUen("1");
        product.setShop(shop);
        return  product;
    }

    private Order getOrder(){
        Order order=new Order();
        order.setOrderReferenceId("456789820");
        order.setStatus(OrderStatus.PLACED);
        OrderItem orderItem=new OrderItem();
        orderItem.setOrder(order);
        orderItem.setId(1L);
        orderItem.setQuantity(4);
        return order;
    }
}