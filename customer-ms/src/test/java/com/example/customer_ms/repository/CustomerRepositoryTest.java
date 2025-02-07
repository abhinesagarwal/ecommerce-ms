package com.example.customer_ms.repository;

import com.example.customer_ms.entity.CustomerEntity;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindAll() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customerRepository.save(customer);

        // Act
        List<CustomerEntity> result = customerRepository.findAll();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customerRepository.save(customer);

        // Act
        CustomerEntity result = customerRepository.findById(customer.getId()).orElseThrow();

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testSave() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();

        // Act
        CustomerEntity result = customerRepository.save(customer);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
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
        customer.setEmail("abhinesh@example.com");
        customerRepository.save(customer);

        // Act
        CustomerEntity result = customerRepository.findByEmail("abhinesh@example.com").orElseThrow();

        // Assert
        assertNotNull(result);
        assertEquals("abhinesh@example.com", result.getEmail());
    }
}
