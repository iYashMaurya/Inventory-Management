package com.maurya.inventorymanagement.productmanagement.controller;

import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.services.InventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryServices services;
    @PostMapping
    public boolean createProduct (@RequestBody ProductEntity entity) {
        services.createProduct(entity);
        return true;
    }

    @GetMapping("/{id}")
    public ProductEntity getProductByID (@PathVariable Long id) {
       return services.getProductByID(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct (@PathVariable Long id) {
        return services.deleteProductByID(id);
    }

    @PutMapping("/{id}")
    public ProductEntity updateProductByID (@PathVariable Long id, @RequestBody ProductEntity entity) {
        return services.updateProductByID(id, entity);
    }
}
