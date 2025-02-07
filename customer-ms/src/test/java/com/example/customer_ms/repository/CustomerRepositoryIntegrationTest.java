package com.example.customer_ms.repository;

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
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindAll() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");
        customerRepository.save(customer);

        // Act
        List<CustomerEntity> customers = customerRepository.findAll();

        // Assert
        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    @Test
    public void testFindById() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");
        customerRepository.save(customer);

        // Act
        CustomerEntity foundCustomer = customerRepository.findById(customer.getId()).orElseThrow();

        // Assert
        assertNotNull(foundCustomer);
        assertEquals("Abhinesh", foundCustomer.getName());
        assertEquals("abhinesh@example.com", foundCustomer.getEmail());
    }

    @Test
    public void testSave() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        // Act
        CustomerEntity savedCustomer = customerRepository.save(customer);

        // Assert
        assertNotNull(savedCustomer);
        assertEquals("Abhinesh", savedCustomer.getName());
        assertEquals("abhinesh@example.com", savedCustomer.getEmail());
    }

    @Test
    public void testDelete() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");
        customerRepository.save(customer);

        // Act
        customerRepository.deleteById(customer.getId());

        // Assert
        assertFalse(customerRepository.existsById(customer.getId()));
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");
        customerRepository.save(customer);

        // Act
        CustomerEntity foundCustomer = customerRepository.findByEmail("abhinesh@example.com").orElseThrow();

        // Assert
        assertNotNull(foundCustomer);
        assertEquals("Abhinesh", foundCustomer.getName());
        assertEquals("abhinesh@example.com", foundCustomer.getEmail());
    }
}