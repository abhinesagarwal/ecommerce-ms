package com.example.customer_ms.dto;

import com.example.customer_ms.validator.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String id;

    @NotEmpty(message = "Name is required")
    private String name;

    @ValidEmail
    @NotEmpty(message = "Email is required")
    private String email;
}

