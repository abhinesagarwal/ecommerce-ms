package com.example.order_ms.controller;

import com.example.order_ms.dto.OrderRequestDTO;
import com.example.order_ms.entity.OrderEntity;
import com.example.order_ms.response.OrderResponse;
import com.example.order_ms.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testGetAllOrders() {
        // Arrange
        List<OrderResponse> orderResponses = List.of(new OrderResponse(), new OrderResponse());
        when(orderService.getAllOrders()).thenReturn(orderResponses);

        // Act
        List<OrderResponse> response = orderController.getAllOrders();

        // Assert
        assertEquals(orderResponses, response);
    }

    @Test
    void testGetOrderById() {
        // Arrange
        OrderResponse orderResponse = new OrderResponse();
        when(orderService.getOrderById(any(String.class))).thenReturn(orderResponse);

        // Act
        OrderResponse response = orderController.getOrderById("1");

        // Assert
        assertEquals(orderResponse, response);
    }

    @Test
    void testGetOrderByIdFallback() {
        // Arrange
        OrderResponse orderResponse = new OrderResponse();
        // Assuming these methods exist in OrderResponse class
        orderResponse.setProductNotFoundError(true);
        orderResponse.setCustomerNotFoundError(true);

        // Act
        OrderResponse response = orderController.getOrderByIdFallback("1", new Exception("Test exception"));

        // Assert
        // Assuming these methods exist in OrderResponse class
        assertEquals(orderResponse.isProductNotFoundError(), response.isProductNotFoundError());
        assertEquals(orderResponse.isCustomerNotFoundError(), response.isCustomerNotFoundError());
    }

    @Test
    void testAddOrder() {
        // Arrange
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        OrderResponse orderResponse = new OrderResponse();
        when(orderService.addOrder(any(OrderRequestDTO.class))).thenReturn(orderResponse);

        // Act
        OrderResponse response = orderController.addOrder(orderRequestDTO);

        // Assert
        assertEquals(orderResponse, response);
    }

    @Test
    void testUpdateOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        OrderResponse orderResponse = new OrderResponse();
        when(orderService.updateOrder(any(String.class), any(OrderEntity.class))).thenReturn(orderResponse);

        // Act
        OrderResponse response = orderController.updateOrder("1", orderEntity);

        // Assert
        assertEquals(orderResponse, response);
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        OrderResponse orderResponse = new OrderResponse();
        when(orderService.deleteOrder(any(String.class))).thenReturn(orderResponse);

        // Act
        OrderResponse response = orderController.deleteOrder("1");

        // Assert
        assertEquals(orderResponse, response);
    }
}
