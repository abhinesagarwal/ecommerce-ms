package com.example.customer_ms.controller;

import com.example.customer_ms.entity.CustomerEntity;
import com.example.customer_ms.service.CustomerService;
import com.example.customer_ms.dto.CustomerDTO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void testGetAllCustomers() {
        // Arrange
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customers);

        // Act
        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testGetCustomerById_CustomerNotFound() {
        // Arrange
        when(customerService.getCustomerById("id")).thenReturn(null);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> customerController.getCustomerById("id"));
    }


    @Test
    public void testAddCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setId("1");
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        when(customerService.addCustomer(customer)).thenReturn(customer);

        // Act
        ResponseEntity<CustomerEntity> response = customerController.addCustomer(customer);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        when(customerService.updateCustomer("id", customer)).thenReturn(customer);

        // Act
        ResponseEntity<CustomerEntity> response = customerController.updateCustomer("id", customer);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        doNothing().when(customerService).deleteCustomer("id");

        // Act
        ResponseEntity<Void> response = customerController.deleteCustomer("id");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}