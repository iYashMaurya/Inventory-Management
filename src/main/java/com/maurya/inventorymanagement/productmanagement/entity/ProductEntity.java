package com.maurya.inventorymanagement.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "product_entity")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    private String description;

    @NotNull
    @Min(value = 1, message = "Stock quantity cannot be less than 1")
    @Column(name = "stock_quantity")
    private long stockQuantity;
}