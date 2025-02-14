package com.example.product_ms.repository;

import com.example.product_ms.entity.ProductEntity;
import com.example.product_ms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setName("Test product");

        // Act
        ProductEntity savedProduct = productRepository.save(productEntity);

        // Assert
        assertEquals(productEntity.getId(), savedProduct.getId());
        assertEquals(productEntity.getName(), savedProduct.getName());
    }

    @Test
    void testFindProductById() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setName("Test product");
        productRepository.save(productEntity);

        // Act
        ProductEntity foundProduct = productRepository.findById("1").orElse(null);

        // Assert
        assertEquals(productEntity.getId(), foundProduct.getId());
        assertEquals(productEntity.getName(), foundProduct.getName());
    }
}
