package com.example.product_ms.service;

import com.example.product_ms.dto.InventoryDTO;
import com.example.product_ms.dto.ProductDTO;
import com.example.product_ms.entity.InventoryEntity;
import com.example.product_ms.entity.ProductEntity;

import com.example.product_ms.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class InventoryService {


    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setQuantity(inventoryDTO.getQuantity());
        InventoryEntity savedInventory = inventoryRepository.save(inventory);
        InventoryDTO response = new InventoryDTO();
        response.setId(savedInventory.getId());
        response.setQuantity(savedInventory.getQuantity());

        return response;
    }

    public InventoryDTO updateInventory(String id, InventoryDTO inventoryDTO) {
        InventoryEntity existingInventory = inventoryRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with ID: " + id));
        existingInventory.setQuantity(inventoryDTO.getQuantity());
        InventoryEntity updatedInventory = inventoryRepository.save(existingInventory);
        InventoryDTO response = new InventoryDTO();
        response.setId(updatedInventory.getId());
        response.setQuantity(updatedInventory.getQuantity());
        return response;
    }

    public List<InventoryDTO> getAllInventory() {
        List<InventoryEntity> inventories = inventoryRepository.findAll();
        if (inventories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No inventory found");
        }
        List<InventoryDTO> inventoryDTOs = new ArrayList<>();
        for (InventoryEntity inventory : inventories) {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setId(inventory.getId());
            inventoryDTO.setQuantity(inventory.getQuantity());

            inventoryDTOs.add(inventoryDTO);
        }
        return inventoryDTOs;
    }

    public InventoryDTO getInventoryById(String id) {
        InventoryEntity inventory = inventoryRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with ID: " + id));
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setQuantity(inventory.getQuantity());

        return inventoryDTO;
    }

    private ProductDTO mapProductDTO(ProductEntity product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setProductType(product.getProductType());
        productDTO.setProductUrl(product.getProductUrl());
        return productDTO;
    }
}
