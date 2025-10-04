package com.maurya.inventorymanagement.productmanagement.services;

import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServices {
    @Autowired
    private ProductRepository repository;

    public ProductEntity createProduct(ProductEntity product) {
        repository.save(product);
        return product;
    }

    public ProductEntity getProductByID (Long id) {
        return repository.findById(id).orElseThrow();
    }

    public boolean deleteProductByID (Long id) {
        repository.deleteById(id);
        return repository.findById(id).isPresent();
    }

    public ProductEntity updateProductByID(Long id, ProductEntity entity) {
        ProductEntity oldProduct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (entity.getProductName() != null && !entity.getProductName().isEmpty()) {
            oldProduct.setProductName(entity.getProductName());
        }
        if (entity.getDescription() != null && !entity.getDescription().isEmpty()) {
            oldProduct.setDescription(entity.getDescription());
        }
        if (entity.getStockQuantity() > 0) { // optional: avoid updating to 0 unless allowed
            oldProduct.setStockQuantity(entity.getStockQuantity());
        }

        return repository.save(oldProduct); // <--- important
    }
}
