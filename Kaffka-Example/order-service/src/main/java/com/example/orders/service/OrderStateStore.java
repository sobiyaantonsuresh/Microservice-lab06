package com.example.orders.service;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderStateStore {
    private final Map<String, String> orderStatuses = new ConcurrentHashMap<>();

    public void setStatus(String orderId, String status) {
        this.orderStatuses.put(orderId, status);
    }

    public String getStatus(String orderId) {
        return this.orderStatuses.getOrDefault(orderId, "UNKNOWN");
    }
}