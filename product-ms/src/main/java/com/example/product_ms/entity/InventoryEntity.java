package com.example.product_ms.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventories")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int quantity;


}
