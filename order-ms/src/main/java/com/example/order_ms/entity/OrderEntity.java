package com.example.order_ms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String customerName;
    private String productName;
    private int quantity;
    private double orderTotal;

    @Transient
    @JsonIgnore
    private String customerId;
    @Transient
    @JsonIgnore
    private String productId;
}
