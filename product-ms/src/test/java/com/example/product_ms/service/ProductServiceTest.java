package com.example.product_ms.service;

import com.example.product_ms.dto.InventoryDTO;
import com.example.product_ms.dto.ProductDTO;
import com.example.product_ms.entity.InventoryEntity;
import com.example.product_ms.entity.ProductEntity;
import com.example.product_ms.repository.ProductRepository;
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
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        // Arrange
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setName("Test product");

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setName("Test product2");

        when(productRepository.findAll()).thenReturn(List.of(productEntity1, productEntity2));

        // Act
        List<ProductDTO> productDTOs = productService.getAllProducts();

        // Assert
        assertEquals(2, productDTOs.size());
    }

    @Test
    void testGetProductById() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Test product");

        when(productRepository.findById(any(String.class))).thenReturn(Optional.of(productEntity));

        // Act
        ProductDTO productDTO = productService.getProductById("1");

        // Assert
        assertEquals(productEntity.getName(), productDTO.getName());
    }

    @Test
    void testAddProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test product");

        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(10);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Test product");

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setQuantity(10);

        when(inventoryService.createInventory(any(InventoryDTO.class))).thenReturn(inventoryDTO);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // Act
        ProductDTO createdProduct = productService.addProduct(productDTO);

        // Assert
        assertEquals(productDTO.getName(), createdProduct.getName());
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test product");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Test product2");

        when(productRepository.findById(any(String.class))).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // Act
        ProductDTO updatedProduct = productService.updateProduct("1", productDTO);

        // Assert
        assertEquals(productDTO.getName(), updatedProduct.getName());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        when(productRepository.findById(any(String.class))).thenReturn(Optional.of(new ProductEntity()));

        // Act
        productService.deleteProduct("1");
    }
}
