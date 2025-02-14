package com.example.product_ms.controller;

import com.example.product_ms.controller.InventoryController;
import com.example.product_ms.dto.InventoryDTO;
import com.example.product_ms.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @Test
    void testUpdateInventory() {
        // Arrange
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId("1");
        inventoryDTO.setQuantity(10);
        when(inventoryService.updateInventory(anyString(), any(InventoryDTO.class))).thenReturn(inventoryDTO);

        // Act
        InventoryDTO result = inventoryController.updateInventory("1", inventoryDTO);
        ResponseEntity<InventoryDTO> response = ResponseEntity.ok(result);

        // Assert
        assertEquals(inventoryDTO, response.getBody());
    }

    @Test
    void testGetAllInventory() {
        // Arrange
        List<InventoryDTO> inventoryDTOList = List.of(new InventoryDTO(), new InventoryDTO());
        when(inventoryService.getAllInventory()).thenReturn(inventoryDTOList);

        // Act
        ResponseEntity<List<InventoryDTO>> response = ResponseEntity.ok(inventoryController.getAllInventory());

        // Assert
        assertEquals(inventoryDTOList, response.getBody());
    }

    @Test
    void testGetInventoryById() {
        // Arrange
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId("1");
        when(inventoryService.getInventoryById(anyString())).thenReturn(inventoryDTO);

        // Act
        ResponseEntity<InventoryDTO> response = ResponseEntity.ok(inventoryController.getInventoryById("1"));

        // Assert
        assertEquals(inventoryDTO, response.getBody());
    }
}
