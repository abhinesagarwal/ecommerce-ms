package com.example.order_ms.repository;

import com.example.order_ms.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSaveOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId("1");
        orderEntity.setProductId("P1");
        orderEntity.setCustomerId("C1");

        // Act
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(orderEntity.getId(), savedOrder.getId());
        assertEquals(orderEntity.getProductId(), savedOrder.getProductId());
        assertEquals(orderEntity.getCustomerId(), savedOrder.getCustomerId());
    }

    @Test
    void testFindOrderById() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId("1");
        orderEntity.setProductId("P1");
        orderEntity.setCustomerId("C1");
        orderRepository.save(orderEntity);

        // Act
        OrderEntity foundOrder = orderRepository.findById("1").orElse(null);

        // Assert
        assertNotNull(foundOrder);
        assertEquals(orderEntity.getId(), foundOrder.getId());
        assertEquals(orderEntity.getProductId(), foundOrder.getProductId());
        assertEquals(orderEntity.getCustomerId(), foundOrder.getCustomerId());
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId("1");
        orderEntity.setProductId("P1");
        orderEntity.setCustomerId("C1");
        orderRepository.save(orderEntity);

        // Act
        orderRepository.deleteById("1");

        // Assert
        assertEquals(0, orderRepository.count());
    }
}
