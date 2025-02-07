package com.example.product_ms.service;

import com.example.product_ms.dto.ProductDTO;
import com.example.product_ms.entity.ProductEntity;
import com.example.product_ms.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(topics = "orders", groupId = "order-ms")
    public void receiveOrderNotification(String message) {
        System.out.println("Received order notification: " + message);
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setProductType(product.getProductType());
            productDTO.setProductUrl(product.getProductUrl());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public ProductDTO getProductById(String id) {
        ProductEntity product = productRepository.findById(id).orElseThrow();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setProductType(product.getProductType());
        productDTO.setProductUrl(product.getProductUrl());

        return productDTO;
    }

    public ProductEntity addProduct(@Valid ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity getProductByProductId(String id) {
        return productRepository.findById(id).orElseThrow();
    }


    public ProductEntity updateProduct(String id, ProductEntity product) {
        ProductEntity existingProduct = getProductByProductId(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductType(product.getProductType());
        existingProduct.setProductUrl(product.getProductUrl());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}