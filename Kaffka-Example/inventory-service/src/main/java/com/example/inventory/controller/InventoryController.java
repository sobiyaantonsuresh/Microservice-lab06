package com.example.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("service", "inventory-service", "status", "UP");
    }
}