package com.example.product_ms.service;

import com.example.product_ms.dto.InventoryDTO;
import com.example.product_ms.entity.InventoryEntity;
import com.example.product_ms.repository.InventoryRepository;
import com.example.product_ms.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void testCreateInventory() {
        // Arrange
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(10);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setQuantity(10);

        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventoryEntity);

        // Act
        InventoryDTO createdInventory = inventoryService.createInventory(inventoryDTO);

        // Assert
        assertEquals(inventoryDTO.getQuantity(), createdInventory.getQuantity());
    }

    @Test
    void testUpdateInventory() {
        // Arrange
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(10);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setQuantity(5);

        when(inventoryRepository.findById(any(String.class))).thenReturn(Optional.of(inventoryEntity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventoryEntity);

        // Act
        InventoryDTO updatedInventory = inventoryService.updateInventory("1", inventoryDTO);

        // Assert
        assertEquals(inventoryDTO.getQuantity(), updatedInventory.getQuantity());
    }

    @Test
    void testGetAllInventory() {
        // Arrange
        InventoryEntity inventoryEntity1 = new InventoryEntity();
        inventoryEntity1.setQuantity(10);

        InventoryEntity inventoryEntity2 = new InventoryEntity();
        inventoryEntity2.setQuantity(20);

        when(inventoryRepository.findAll()).thenReturn(List.of(inventoryEntity1, inventoryEntity2));

        // Act
        List<InventoryDTO> inventoryDTOs = inventoryService.getAllInventory();

        // Assert
        assertEquals(2, inventoryDTOs.size());
    }

    @Test
    void testGetInventoryById() {
        // Arrange
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setQuantity(10);

        when(inventoryRepository.findById(any(String.class))).thenReturn(Optional.of(inventoryEntity));

        // Act
        InventoryDTO inventoryDTO = inventoryService.getInventoryById("1");

        // Assert
        assertEquals(inventoryEntity.getQuantity(), inventoryDTO.getQuantity());
    }
}
