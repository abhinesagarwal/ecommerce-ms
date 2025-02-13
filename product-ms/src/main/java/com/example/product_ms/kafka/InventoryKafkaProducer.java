package com.example.product_ms.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryKafkaProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public InventoryKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendQuantityEvent(String message) {
        kafkaTemplate.send("inventories", message);
    }
}
