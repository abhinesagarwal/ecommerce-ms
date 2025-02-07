package com.example.customer_ms.controller;

import com.example.customer_ms.dto.CustomerDTO;
import com.example.customer_ms.entity.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
public class CustomerControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllCustomers() {
        // Act
        webTestClient.get().uri("/api/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDTO.class);
    }

    @Test
    public void testGetCustomerById() {
        // Act
        webTestClient.get().uri("/api/customers/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDTO.class)
                .consumeWith(response -> {
                    assertEquals("Abhinesh", Objects.requireNonNull(response.getResponseBody()).getName());
                    assertEquals("abhinesh@example.com", response.getResponseBody().getEmail());
                });
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Abhinesh");
        customer.setEmail("abhinesh@example.com");

        // Act
        webTestClient.post().uri("/api/customers")
                .bodyValue(customer)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerEntity.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals("Abhinesh", response.getResponseBody().getName());
                    assertEquals("abhinesh@example.com", response.getResponseBody().getEmail());
                });
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
        webTestClient.put().uri("/api/customers/1")
                .bodyValue(updatedCustomer)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerEntity.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals("Abhinesh", response.getResponseBody().getName());
                    assertEquals("abhinesh@example.com", response.getResponseBody().getEmail());
                });
    }

    @Test
    public void testDeleteCustomer() {
        // Act
        webTestClient.delete().uri("/api/customers/1")
                .exchange()
                .expectStatus().isNoContent();

        // Assert
        webTestClient.get().uri("/api/customers/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}