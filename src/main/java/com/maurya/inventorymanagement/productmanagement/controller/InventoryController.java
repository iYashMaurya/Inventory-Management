package com.maurya.inventorymanagement.productmanagement.controller;

import com.maurya.inventorymanagement.productmanagement.dto.ProductRequestDTO;
import com.maurya.inventorymanagement.productmanagement.dto.ProductResponseDTO;
import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.services.InventoryServices;
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
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(request.getProductName());
        entity.setDescription(request.getDescription());
        entity.setStockQuantity(request.getStockQuantity());

        ProductResponseDTO response = services.createProduct(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(services.getProductByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
                                                       @Valid @RequestBody ProductRequestDTO request) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(request.getProductName());
        entity.setDescription(request.getDescription());
        entity.setStockQuantity(request.getStockQuantity());

        return ResponseEntity.ok(services.updateProductByID(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        services.deleteProductByID(id);
        return ResponseEntity.noContent().build();
    }
}