package com.example.product_ms.kafka;

import com.example.product_ms.response.OrderResponse;
import com.example.product_ms.entity.InventoryEntity;
import com.example.product_ms.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class ProductKafkaConsumer {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryKafkaProducer inventoryKafkaProducer;

    private static final String HOST = "http://localhost:8084/api";

    @Transactional
    @KafkaListener(topics = "orders", groupId = "product-service")
    public void receiveOrderEvent(String message) {
        String[] parts = message.split(":");
        String orderId = parts[1].trim();
        System.out.println("Received message: " + message);
        System.out.println("order: " + orderId);

        // Call Order Service API to get order details
        RestTemplate restTemplate = new RestTemplate();
        String url = HOST + "/orders/" + orderId;
        OrderResponse order = restTemplate.getForObject(url, OrderResponse.class);

        if (order != null && order.getProduct() != null) {
            // Update inventory quantity
            System.out.println("fetch id: " + order.getProduct().getInventory().getId());
            Optional<InventoryEntity> inventoryOptional = inventoryRepository.findById(order.getProduct().getInventory().getId());
            System.out.println("inventoryOptional = " + inventoryOptional);
            if (inventoryOptional.isPresent()) {
                InventoryEntity inventory = inventoryOptional.get();
                inventory.setQuantity(inventory.getQuantity() - order.getOrder().getOrderQuantity());
                System.out.println("order.getOrder().getOrderQuantity() = " + order.getOrder().getOrderQuantity());
                InventoryEntity savedInventory = inventoryRepository.save(inventory);
                System.out.println("savedInventory = " + savedInventory.getQuantity());
                inventoryKafkaProducer.sendQuantityEvent("Remaining Inventory updated: " + savedInventory.getQuantity());
            } else {
                log.error("Inventory not found for product {}", order.getProduct().getId());
            }
        } else {
            log.error("Order or Product details not found for order {}", orderId);
        }
    }
}