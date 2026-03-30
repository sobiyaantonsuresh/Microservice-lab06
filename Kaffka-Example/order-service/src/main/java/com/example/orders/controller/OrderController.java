package com.example.orders.controller;

import com.example.orders.model.OrderRequest;
import com.example.orders.service.OrderEventPublisher;
import com.example.orders.service.OrderStateStore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderEventPublisher orderEventPublisher;
    private final OrderStateStore orderStateStore;

    public OrderController(OrderEventPublisher orderEventPublisher, OrderStateStore orderStateStore) {
        this.orderEventPublisher = orderEventPublisher;
        this.orderStateStore = orderStateStore;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, String> createOrder(@Valid @RequestBody OrderRequest request) {
        
        this.orderStateStore.setStatus(request.orderId(), "CREATED");
        
        
        this.orderEventPublisher.publishOrderCreated(request);
        
        return Map.of(
            "orderId", request.orderId(),
            "status", "CREATED",
            "message", "Order accepted and event published"
        );
    }

    @GetMapping("/{orderId}")
    public Map<String, String> getOrderStatus(@PathVariable String orderId) {
      
        String status = this.orderStateStore.getStatus(orderId);
        return Map.of("orderId", orderId, "status", status != null ? status : "NOT_FOUND");
    }
}