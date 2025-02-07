package com.example.customer_ms.service;

import com.example.customer_ms.dto.CustomerDTO;

import com.example.customer_ms.entity.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() {
        // Act
        List<CustomerDTO> customers = customerService.getAllCustomers();

        // Assert
        assertNotNull(customers);
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setId("1");
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        // Act
        CustomerDTO customerDTO = customerService.getCustomerById("1");

        // Assert
        assertNotNull(customerDTO);
        assertEquals("Abhinesh", customerDTO.getName());
        assertEquals("abhinesh@example.com", customerDTO.getEmail());
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        // Act
        CustomerEntity addedCustomer = customerService.addCustomer(customer);

        // Assert
        assertNotNull(addedCustomer);
        assertEquals("Abhinesh", addedCustomer.getName());
        assertEquals("abhinesh@example.com", addedCustomer.getEmail());
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setId("1");
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setName("Abhinesh");
        updatedCustomer.setEmail("abhinesh@example.com");

        // Act
        CustomerEntity updatedCustomerEntity = customerService.updateCustomer("1", updatedCustomer);

        // Assert
        assertNotNull(updatedCustomerEntity);
        assertEquals("Abhinesh", updatedCustomerEntity.getName());
        assertEquals("abhinesh@example.com", updatedCustomerEntity.getEmail());
    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setId("1");
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        // Act
        customerService.deleteCustomer("1");

        // Assert
        CustomerDTO deletedCustomer = customerService.getCustomerById("1");
        assertNull(deletedCustomer);
    }
}
