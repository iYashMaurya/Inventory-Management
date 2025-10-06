package com.maurya.inventorymanagement.productmanagement.controller;

import com.maurya.inventorymanagement.productmanagement.dto.QuantityUpdateDTO;
import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.services.ProductServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServices services;

    @PutMapping("/increase-quantity/{id}")
    @Operation(summary = "Increase product stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock increased successfully",
                    content = @Content(examples = @ExampleObject(value = "\"Updated product with id: 1 with stock quantity: 15\""))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(examples = @ExampleObject(value = "\"Product not found\"")))
    })
    public ResponseEntity<String> increaseQuantity(@PathVariable Long id,
                                                   @Valid @RequestBody QuantityUpdateDTO quantity) {
        return ResponseEntity.ok(services.increaseStockByID(id, quantity.getQuantity()));
    }

    @PutMapping("/decrease-quantity/{id}")
    @Operation(summary = "Decrease product stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock decreased successfully",
                    content = @Content(examples = @ExampleObject(value = "\"Updated product with id: 1 with stock quantity: 5\""))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(examples = @ExampleObject(value = "\"Product not found\""))),
            @ApiResponse(responseCode = "400", description = "Insufficient stock quantity",
                    content = @Content(examples = @ExampleObject(value = "\"Insufficient stock quantity for product with id: 1\"")))
    })
    public ResponseEntity<String> decreaseQuantity(@PathVariable Long id,
                                                   @Valid @RequestBody QuantityUpdateDTO quantity) {
        return ResponseEntity.ok(services.decreaseStockByID(id, quantity.getQuantity()));
    }

    @GetMapping("/low-threshold")
    public ResponseEntity<List<ProductEntity>> lowThresholdValue() {
        return ResponseEntity.ok(services.lowThreshold());
    }
}