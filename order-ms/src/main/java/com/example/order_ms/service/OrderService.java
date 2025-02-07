package com.example.order_ms.service;

import com.example.order_ms.dto.CustomerDTO;
import com.example.order_ms.dto.OrderRequestDTO;
import com.example.order_ms.dto.ProductDTO;
import com.example.order_ms.entity.OrderEntity;
import com.example.order_ms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public OrderEntity addOrder(OrderRequestDTO orderRequest) {
        // Call ProductService to get product details
        ProductDTO product = restTemplate.getForObject("http://localhost:8082/api/products/" + orderRequest.getProductId(), ProductDTO.class);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        // Call CustomerService to get customer details
        CustomerDTO customer = restTemplate.getForObject("http://localhost:8081/api/customers/" + orderRequest.getCustomerId(), CustomerDTO.class);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        OrderEntity order = new OrderEntity();
        order.setProductName(product.getName());
        order.setOrderTotal(product.getPrice() * orderRequest.getQuantity());
        order.setQuantity(orderRequest.getQuantity());
        order.setCustomerName(customer.getName());

        OrderEntity savedOrder = orderRepository.save(order);
        kafkaTemplate.send("orders", "Order placed: " + savedOrder.getId());
        return savedOrder;
    }

    public OrderEntity updateOrder(String id, OrderEntity order) {
        OrderEntity existingOrder = getOrderById(id);
        existingOrder.setQuantity(order.getQuantity());
        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        kafkaTemplate.send("orders", "Order updated: " + updatedOrder.getId());
        return updatedOrder;
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
        kafkaTemplate.send("orders", "Order deleted: " + id);
    }
}