package com.example.order_ms.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int orderQuantity;
    private double orderTotal;
    private String customerId;

    private String productId;
}
