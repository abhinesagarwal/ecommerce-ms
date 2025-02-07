package com.example.order_ms.controller;

import com.example.order_ms.dto.OrderRequestDTO;
import com.example.order_ms.entity.OrderEntity;
import com.example.order_ms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderEntity addOrder(@RequestBody OrderRequestDTO order) {
        return orderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(@PathVariable String id, @RequestBody OrderEntity order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
}