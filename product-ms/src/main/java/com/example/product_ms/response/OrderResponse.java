package com.example.product_ms.response;

import com.example.product_ms.dto.OrderDTO;
import com.example.product_ms.dto.ProductDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private OrderDTO order;
    private ProductDTO product;
    private boolean productNotFoundError;
    private boolean customerNotFoundError;
}