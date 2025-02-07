package com.example.customer_ms.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaListener {

    @KafkaListener(topics = "order_topic", groupId = "customer-group")
    public void receiveOrderMessage(String message) {
        System.out.println("Received order message: " + message);
        // Process the order message
    }
}