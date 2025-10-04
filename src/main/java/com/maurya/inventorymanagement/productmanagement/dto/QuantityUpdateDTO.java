package com.maurya.inventorymanagement.productmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantityUpdateDTO {
    @NotNull
    @Min(value = 1, message = "Value cannot be smaller than 1")
    private int quantity;
}
