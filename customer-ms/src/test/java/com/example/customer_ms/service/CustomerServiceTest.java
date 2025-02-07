package com.example.customer_ms.service;

import com.example.customer_ms.entity.CustomerEntity;
import com.example.customer_ms.repository.CustomerRepository;
import com.example.customer_ms.dto.CustomerDTO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() {
        // Arrange
        List<CustomerEntity> customers = new ArrayList<>();
        customers.add(new CustomerEntity());
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<CustomerDTO> result = customerService.getAllCustomers();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO result = customerService.getCustomerById("id");

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        assertNotNull(customerService); // Verify customerService is not null
        when(customerService.addCustomer(customer)).thenReturn(new CustomerEntity()); // Stub the addCustomer method
        // Act
        CustomerEntity result = customerService.addCustomer(customer);
        // Assert
        assertNotNull(result); // Verify result is not null
        assertEquals(customer, result); // Verify result is equal to input customer
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setId("id");
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setId("id");
        updatedCustomer.setName("Abhines");
        updatedCustomer.setEmail("abhines@example.com");

        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // Act
        CustomerEntity result = customerService.updateCustomer("id", updatedCustomer);

        // Assert
        assertNotNull(result);
        assertEquals(updatedCustomer.getName(), result.getName());
        assertEquals(updatedCustomer.getEmail(), result.getEmail());
    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        doNothing().when(customerRepository).deleteById("id");

        // Act
        customerService.deleteCustomer("id");

        // Assert
        verify(customerRepository, times(1)).deleteById("id");
    }
}
