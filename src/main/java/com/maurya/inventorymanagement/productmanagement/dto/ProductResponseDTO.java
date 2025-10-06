package com.maurya.inventorymanagement.productmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private String description;
    private Long stockQuantity;
    private long thresholdQuantity;
    private String message;
}