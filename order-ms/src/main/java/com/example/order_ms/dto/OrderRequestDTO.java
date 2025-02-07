package com.example.order_ms.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private String customerId;
    private String productId;
    private int quantity;
}
