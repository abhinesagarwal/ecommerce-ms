package com.example.order_ms.service;

import com.example.order_ms.dto.CustomerDTO;
import com.example.order_ms.dto.InventoryDTO;
import com.example.order_ms.dto.OrderRequestDTO;
import com.example.order_ms.dto.ProductDTO;
import com.example.order_ms.entity.OrderEntity;
import com.example.order_ms.kafka.OrderKafkaProducer;
import com.example.order_ms.repository.OrderRepository;
import com.example.order_ms.response.OrderResponse;
import org.mockito.ArgumentMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OrderKafkaProducer orderKafkaProducer;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetAllOrders() {
        // Arrange
        List<OrderEntity> orders = List.of(new OrderEntity(), new OrderEntity());
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<OrderResponse> orderResponses = orderService.getAllOrders();

        // Assert
        assertEquals(orders.size(), orderResponses.size());
    }

    @Test
    void testGetOrderById() {
        // Arrange
        OrderEntity order = new OrderEntity();
        when(orderRepository.findById(any(String.class))).thenReturn(Optional.of(order));

        // Act
        OrderResponse orderResponse = orderService.getOrderById("1");

        // Assert
        assertEquals(order, orderResponse.getOrder());
    }

    @Test
    void testAddOrder() {
        // Arrange
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setProductId("P1");
        orderRequest.setCustomerId("C1");
        orderRequest.setOrderQuantity(2);

        ProductDTO product = new ProductDTO();
        product.setId("P1");
        product.setPrice(10.99);
        // Assuming InventoryDTO is an inner class of ProductDTO
        product.setInventory(new InventoryDTO(10));

        CustomerDTO customer = new CustomerDTO();
        customer.setId("C1");

        when(restTemplate.getForObject(any(String.class), ArgumentMatchers.eq(ProductDTO.class))).thenReturn(product);
        when(restTemplate.getForObject(any(String.class), ArgumentMatchers.eq(CustomerDTO.class))).thenReturn(customer);

        // Act
        OrderResponse orderResponse = orderService.addOrder(orderRequest);

        // Assert
        assertEquals(orderRequest.getProductId(), orderResponse.getOrder().getProductId());
        assertEquals(orderRequest.getCustomerId(), orderResponse.getOrder().getCustomerId());
        assertEquals(orderRequest.getOrderQuantity(), orderResponse.getOrder().getOrderQuantity());
    }

    @Test
    void testAddOrder_InsufficientQuantity() {
        // Arrange
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setProductId("P1");
        orderRequest.setCustomerId("C1");
        orderRequest.setOrderQuantity(20);
        ProductDTO product = new ProductDTO();
        product.setId("P1");
        product.setPrice(10.99);
        product.setInventory(new InventoryDTO(10));
        CustomerDTO customer = new CustomerDTO();
        customer.setId("C1");
        when(restTemplate.getForObject(any(String.class), ArgumentMatchers.eq(ProductDTO.class))).thenReturn(product);
        when(restTemplate.getForObject(any(String.class), ArgumentMatchers.eq(CustomerDTO.class))).thenReturn(customer);

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> orderService.addOrder(orderRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testUpdateOrder() {
        // Arrange
        OrderEntity order = new OrderEntity();
        order.setId("O1");
        order.setProductId("P1");
        order.setCustomerId("C1");
        order.setOrderQuantity(2);

        when(orderRepository.findById(any(String.class))).thenReturn(Optional.of(order));

        // Act
        OrderResponse orderResponse = orderService.updateOrder("O1", new OrderEntity());

        // Assert
        assertEquals(order.getId(), orderResponse.getOrder().getId());
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        OrderEntity order = new OrderEntity();
        order.setId("O1");
        order.setProductId("P1");
        order.setCustomerId("C1");
        order.setOrderQuantity(2);

        when(orderRepository.findById(any(String.class))).thenReturn(Optional.of(order));

        // Act
        OrderResponse orderResponse = orderService.deleteOrder("O1");

        // Assert
        assertEquals(order.getId(), orderResponse.getOrder().getId());
    }
}