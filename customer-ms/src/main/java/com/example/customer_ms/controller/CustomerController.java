package com.example.customer_ms.controller;

import com.example.customer_ms.dto.CustomerDTO;
import com.example.customer_ms.entity.CustomerEntity;
import com.example.customer_ms.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerEntity> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerEntity addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerDTO.getName());
        customerEntity.setEmail(customerDTO.getEmail());
        return customerService.addCustomer(customerEntity);
    }

    @PutMapping("/{id}")
    public CustomerEntity updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }
}