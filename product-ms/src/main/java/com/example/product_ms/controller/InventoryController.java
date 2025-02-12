package com.example.product_ms.controller;

import com.example.product_ms.dto.InventoryDTO;
import com.example.product_ms.service.InventoryService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/{id}")
    public InventoryDTO updateInventory(@PathVariable String id, @RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventory(id, inventoryDTO);
    }

    @GetMapping
    public List<InventoryDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryDTO getInventoryById(@PathVariable String id) {
        return inventoryService.getInventoryById(id);
    }
}