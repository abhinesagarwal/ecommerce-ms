package com.example.order_ms.controller;

import com.example.order_ms.dto.OrderRequestDTO;
import com.example.order_ms.entity.OrderEntity;
import com.example.order_ms.response.OrderResponse;
import com.example.order_ms.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ibm.java.diagnostics.utils.Context.logger;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "order-ms-circuit-breaker", fallbackMethod = "getOrderByIdFallback")
    public OrderResponse getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    //creating fallback method for circuit breaker
    public OrderResponse getOrderByIdFallback( String id, Exception ex){
        logger.info("Fallback method executed because order service is not available: {}" + ex.getMessage());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setProductNotFoundError(true);
        orderResponse.setCustomerNotFoundError(true);
        return orderResponse;
    }


    @PostMapping
    public OrderResponse addOrder(@RequestBody OrderRequestDTO order) {
        return orderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public OrderResponse updateOrder(@PathVariable String id, @RequestBody OrderEntity order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public OrderResponse deleteOrder(@PathVariable String id) {
        return orderService.deleteOrder(id);
    }
}