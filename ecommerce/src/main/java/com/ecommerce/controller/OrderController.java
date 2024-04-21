package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequestDto;
import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.dto.OrderStatusDto;
import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Order controller", description = "Order controller")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @Operation(summary = "Place an order")
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto orderRequest) {
        try {
            OrderResponseDto orderResponse = orderService.processOrder(orderRequest);
            return ResponseEntity.ok(orderResponse);
        } catch (InsufficientStockException e) {
            return ResponseEntity.badRequest().body(new OrderResponseDto( null));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @Operation(summary = "Cancel an order")
    @PostMapping("/cancel_order")
    public ResponseEntity<Void> cancelOrder(@NotBlank @RequestParam String orderReferenceId) {
        orderService.cancelOrder(orderReferenceId);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status checked successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @Operation(summary = "Check order status")
    @GetMapping("/check_order_status")
    public ResponseEntity<OrderStatusDto> checkOrderStatus(@NotNull @NotBlank @RequestParam String orderReferenceId) {
        OrderStatusDto orderStatus = orderService.getOrderStatus(orderReferenceId);
        if (orderStatus == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderStatus);
    }
}
