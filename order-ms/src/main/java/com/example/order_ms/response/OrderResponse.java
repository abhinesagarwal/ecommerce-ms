package com.example.order_ms.response;

import com.example.order_ms.dto.CustomerDTO;
import com.example.order_ms.dto.ProductDTO;
import com.example.order_ms.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private OrderEntity order;
    private ProductDTO product;
    private CustomerDTO customer;
    private boolean productNotFoundError;
    private boolean customerNotFoundError;

}
