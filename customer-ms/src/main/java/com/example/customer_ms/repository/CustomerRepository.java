package com.example.customer_ms.repository;

import com.example.customer_ms.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByEmail(String email);
}
