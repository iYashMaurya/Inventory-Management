package com.maurya.inventorymanagement.productmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    private String description;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 1, message = "Stock quantity must be at least 1")
    private Long stockQuantity;

    @NotNull(message = "Threshold value is required")
    @Min(value = 0, message = "Threshold value must be at least 0")
    private long thresholdQuantity;
}