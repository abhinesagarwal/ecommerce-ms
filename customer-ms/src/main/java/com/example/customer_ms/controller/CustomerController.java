package com.example.customer_ms.controller;

import com.example.customer_ms.dto.CustomerDTO;
import com.example.customer_ms.entity.CustomerEntity;
import com.example.customer_ms.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // GET /customers - Retrieve a list of all customers
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // GET /customers/{id} - Retrieve details of a specific customer
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id) {
        System.out.println("Customer Controller : getCustomerById");
        CustomerDTO customer = customerService.getCustomerById(id);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // POST /customers - Add a new customer
    @PostMapping
    public ResponseEntity<CustomerEntity> addCustomer(@Valid @RequestBody CustomerEntity customer) {
        System.out.println("Customer Controller : addCustomer");
        CustomerEntity addedCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    // PUT /customers/{id} - Update an existing customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerEntity customer) {
        System.out.println("Customer Controller : updateCustomer");
        CustomerEntity updatedCustomer = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    // DELETE /customers/{id} - Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        System.out.println("Customer Controller : deleteCustomer");
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
