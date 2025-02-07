package com.example.customer_ms.entity;

import com.example.customer_ms.validator.ValidEmail;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers")
public class CustomerEntity {

    @Id
    private String id;

    @NotEmpty(message = "Name is required")
    private String name;

    @ValidEmail
    @NotEmpty(message = "Email is required")
    private String email;
}