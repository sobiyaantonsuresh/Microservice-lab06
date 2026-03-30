package com.example.inventory.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final ObjectMapper objectMapper;

    public OrderCreatedListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-topic", groupId = "inventory-service-group")
    public void handleOrderCreated(String payload) {
        try {
            JsonNode root = this.objectMapper.readTree(payload);
            String orderId = root.path("orderId").asText();
            String sku = root.path("sku").asText();
            int quantity = root.path("quantity").asInt(0);
            
            boolean reserved = quantity > 0;

            LOGGER.info("Inventory Processed: OrderID={}, SKU={}, Quantity={}, Reserved={}", 
                        orderId, sku, quantity, reserved);
            
            System.out.println("--------------------------------------");
            System.out.println("Inventory Service Received JSON:");
            System.out.println(payload);
            System.out.println("--------------------------------------");

        } catch (Exception ex) {
            LOGGER.error("Failed to process order event: {}", payload, ex);
        }
    }
}