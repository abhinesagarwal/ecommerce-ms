package com.example.product_ms.controller;

import com.example.product_ms.controller.ProductController;
import com.example.product_ms.dto.ProductDTO;
import com.example.product_ms.service.ProductService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testGetAllProducts() {
        // Arrange
        List<ProductDTO> productDTOList = List.of(new ProductDTO(), new ProductDTO());
        when(productService.getAllProducts()).thenReturn(productDTOList);

        // Act
        ResponseEntity<List<ProductDTO>> response = ResponseEntity.ok(productController.getAllProducts());

        // Assert
        assertEquals(productDTOList, response.getBody());
    }

    @Test
    void testGetProductById() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        when(productService.getProductById(anyString())).thenReturn(productDTO);

        // Act
        ResponseEntity<ProductDTO> response = ResponseEntity.ok(productController.getProductById("1"));

        // Assert
        assertEquals(productDTO, response.getBody());
    }

    @Test
    void testAddProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        when(productService.addProduct(any(ProductDTO.class))).thenReturn(productDTO);

        // Act
        ResponseEntity<ProductDTO> response = ResponseEntity.ok(productController.addProduct(productDTO));

        // Assert
        assertEquals(productDTO, response.getBody());
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        when(productService.updateProduct(anyString(), any(ProductDTO.class))).thenReturn(productDTO);

        // Act
        ResponseEntity<ProductDTO> response = ResponseEntity.ok(productController.updateProduct("1", productDTO));

        // Assert
        assertEquals(productDTO, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        doNothing().when(productService).deleteProduct(anyString());

        // Act
        productController.deleteProduct("1");

        // Assert
        // No need to assert anything, just verify that the method was called
        verify(productService).deleteProduct("1");
    }
}
