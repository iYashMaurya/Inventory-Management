package com.maurya.inventorymanagement.productmanagement.controller;

import com.maurya.inventorymanagement.productmanagement.dto.QuantityUpdateDTO;
import com.maurya.inventorymanagement.productmanagement.services.ProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServices services;

    @PutMapping("/increase-quantity/{id}")
    public ResponseEntity<String> increaseQuantity(@PathVariable Long id,
                                                   @Valid @RequestBody QuantityUpdateDTO quantity) {
        return ResponseEntity.ok(services.increaseStockByID(id, quantity.getQuantity()));
    }

    @PutMapping("/decrease-quantity/{id}")
    public ResponseEntity<String> decreaseQuantity(@PathVariable Long id,
                                                   @Valid @RequestBody QuantityUpdateDTO quantity) {
        return ResponseEntity.ok(services.decreaseStockByID(id, quantity.getQuantity()));
    }
}