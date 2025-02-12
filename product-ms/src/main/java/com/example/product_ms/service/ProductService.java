package com.example.product_ms.service;

import com.example.product_ms.dto.InventoryDTO;
import com.example.product_ms.dto.ProductDTO;
import com.example.product_ms.entity.InventoryEntity;
import com.example.product_ms.entity.ProductEntity;
import com.example.product_ms.repository.InventoryRepository;
import com.example.product_ms.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductDTO productDTO = mapProductDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public ProductDTO getProductById(String id) {
        ProductEntity product = productRepository.findById(id).orElseThrow();
        return mapProductDTO(product);
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(0);
        InventoryDTO createdInventory = inventoryService.createInventory(inventoryDTO);

        ProductEntity product = new ProductEntity();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setProductType(productDTO.getProductType());
        product.setProductUrl(productDTO.getProductUrl());

        // Retrieve the InventoryEntity object from the database
        InventoryEntity inventory = inventoryRepository.findById(createdInventory.getId()).orElseThrow();

        // Set the InventoryEntity object to the ProductEntity object
        product.setInventory(inventory);

        // Save the ProductEntity object
        ProductEntity savedProduct = productRepository.save(product);


        return mapProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        ProductEntity existingProduct = productRepository.findById(id).orElseThrow();
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setProductType(productDTO.getProductType());
        existingProduct.setProductUrl(productDTO.getProductUrl());

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return mapProductDTO(updatedProduct);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapProductDTO(ProductEntity product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setProductType(product.getProductType());
        productDTO.setProductUrl(product.getProductUrl());
        if (product.getInventory() != null) {
            productDTO.setInventory(mapInventoryDTO(product.getInventory()));
        }
        return productDTO;
    }

    private InventoryDTO mapInventoryDTO(InventoryEntity inventory) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setQuantity(inventory.getQuantity());
        return inventoryDTO;
    }
}