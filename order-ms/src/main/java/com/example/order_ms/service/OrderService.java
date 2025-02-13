package com.example.order_ms.service;

import com.example.order_ms.dto.CustomerDTO;
import com.example.order_ms.dto.OrderRequestDTO;
import com.example.order_ms.dto.ProductDTO;
import com.example.order_ms.entity.OrderEntity;
import com.example.order_ms.kafka.OrderKafkaProducer;
import com.example.order_ms.repository.OrderRepository;
import com.example.order_ms.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderKafkaProducer orderKafkaProducer;
    private static final String HOST = "http://localhost:8084/api";

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate, OrderKafkaProducer orderKafkaProducer) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.orderKafkaProducer = orderKafkaProducer;
    }

    private ProductDTO getProductDetails(String productId) {
        ProductDTO product = restTemplate.getForObject(HOST + "/products/" + productId, ProductDTO.class);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    private CustomerDTO getCustomerDetails(String customerId) {
        CustomerDTO customer = restTemplate.getForObject(HOST + "/customers/" + customerId, CustomerDTO.class);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        return customer;
    }

    private OrderResponse populateOrderResponse(OrderEntity order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrder(order);
        try {
            ProductDTO product = getProductDetails(order.getProductId());
            orderResponse.setProduct(product);
        } catch (ResponseStatusException e) {
            orderResponse.setProductNotFoundError(true);
        }
        try {
            CustomerDTO customer = getCustomerDetails(order.getCustomerId());
            orderResponse.setCustomer(customer);
        } catch (ResponseStatusException e) {
            orderResponse.setCustomerNotFoundError(true);
        }
        return orderResponse;
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(OrderEntity order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrder(order);
        try {
            ProductDTO product = getProductDetails(order.getProductId());
            orderResponse.setProduct(product);
        } catch (ResponseStatusException e) {
            orderResponse.setProductNotFoundError(true);
        }
        try {
            CustomerDTO customer = getCustomerDetails(order.getCustomerId());
            orderResponse.setCustomer(customer);
        } catch (ResponseStatusException e) {
            orderResponse.setCustomerNotFoundError(true);
        }
        return orderResponse;
    }

    public OrderResponse getOrderById(String id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return populateOrderResponse(order);
    }

    public OrderResponse addOrder(OrderRequestDTO orderRequest) {
        // Call ProductService to get product details
        ProductDTO product = getProductDetails(orderRequest.getProductId());
        // Check if the requested quantity is available
        if (orderRequest.getOrderQuantity() > product.getInventory().getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient quantity available: " + orderRequest.getOrderQuantity() + " > " + product.getInventory().getQuantity());
        }
        // Call CustomerService to get customer details
        System.out.println("Customer ID: " + orderRequest.getCustomerId());
        CustomerDTO customer = getCustomerDetails(orderRequest.getCustomerId());

        OrderEntity order = new OrderEntity();
        order.setProductId(product.getId());
        order.setOrderTotal(product.getPrice() * orderRequest.getOrderQuantity());
        order.setOrderQuantity(orderRequest.getOrderQuantity());
        order.setCustomerId(customer.getId());
        OrderEntity savedOrder = orderRepository.save(order);
        orderKafkaProducer.sendOrderEvent("Order placed: " + savedOrder.getId());
        return populateOrderResponse(savedOrder);
    }

    public OrderResponse updateOrder(String id, OrderEntity order) {
        OrderResponse existingOrderResponse = getOrderById(id);
        OrderEntity existingOrder = existingOrderResponse.getOrder();
        existingOrder.setOrderQuantity(order.getOrderQuantity());
        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        orderKafkaProducer.sendOrderEvent("Order updated: " + updatedOrder.getId());
        return populateOrderResponse(updatedOrder);
    }

    public OrderResponse deleteOrder(String id) {
        OrderResponse orderResponse = getOrderById(id);
        orderRepository.deleteById(id);
        orderKafkaProducer.sendOrderEvent("Order deleted: " + id);
        return orderResponse;
    }
}