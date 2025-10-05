package com.maurya.inventorymanagement.productmanagement.controller;

import com.maurya.inventorymanagement.productmanagement.dto.ProductRequestDTO;
import com.maurya.inventorymanagement.productmanagement.dto.ProductResponseDTO;
import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.services.InventoryServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryServices services;

    @PostMapping
    @Operation(summary = "Create a new product in inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"productId\": 1, \"productName\": \"Soap\", \"message\": \"Product created successfully\" }"
                            ))),
            @ApiResponse(responseCode = "500", description = "Product creation failed",
                    content = @Content(schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Product not saved\"")))
    })
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(request.getProductName());
        entity.setDescription(request.getDescription());
        entity.setStockQuantity(request.getStockQuantity());

        ProductResponseDTO response = services.createProduct(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductEntity.class),
                            examples = @ExampleObject(
                                    value = "{ \"productId\": 1, \"productName\": \"Soap\", \"description\": \"Test Product\", \"stockQuantity\": 10 }"
                            ))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Product not found\"")))
    })
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(services.getProductByID(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductEntity.class),
                            examples = @ExampleObject(
                                    value = "{ \"productId\": 1, \"productName\": \"Updated Soap\", \"description\": \"Updated Description\", \"stockQuantity\": 25 }"
                            ))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Product not found\"")))
    })
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
                                                       @Valid @RequestBody ProductRequestDTO request) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(request.getProductName());
        entity.setDescription(request.getDescription());
        entity.setStockQuantity(request.getStockQuantity());

        return ResponseEntity.ok(services.updateProductByID(id, entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Product deleted successfully\""))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "\"Product not found\"")))
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        services.deleteProductByID(id);
        return ResponseEntity.noContent().build();
    }
}