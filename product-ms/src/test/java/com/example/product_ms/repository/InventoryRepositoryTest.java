package com.example.product_ms.repository;

import com.example.product_ms.entity.InventoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void testSaveInventory() {
        // Arrange
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId("1");
        inventoryEntity.setQuantity(10);

        // Act
        InventoryEntity savedInventory = inventoryRepository.save(inventoryEntity);

        // Assert
        assertEquals(inventoryEntity.getId(), savedInventory.getId());
        assertEquals(inventoryEntity.getQuantity(), savedInventory.getQuantity());
    }

    @Test
    void testFindInventoryById() {
        // Arrange
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId("1");
        inventoryEntity.setQuantity(10);
        inventoryRepository.save(inventoryEntity);

        // Act
        InventoryEntity foundInventory = inventoryRepository.findById("1").orElse(null);

        // Assert
        assert foundInventory != null;
        assertEquals(inventoryEntity.getId(), foundInventory.getId());
        assertEquals(inventoryEntity.getQuantity(), foundInventory.getQuantity());
    }
}
