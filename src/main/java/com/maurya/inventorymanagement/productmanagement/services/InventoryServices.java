package com.maurya.inventorymanagement.productmanagement.services;

import com.maurya.inventorymanagement.productmanagement.dto.ProductResponseDTO;
import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryServices {

    @Autowired
    private ProductRepository repository;

    public ProductResponseDTO createProduct(ProductEntity product) {
        ProductEntity saved = repository.save(product);
        repository.findById(saved.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Product not saved"));

        return new ProductResponseDTO(
                saved.getProductId(),
                saved.getProductName(),
                saved.getDescription(),
                saved.getStockQuantity(),
                "Product created successfully"
        );
    }

    public ProductEntity getProductByID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public void deleteProductByID(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        repository.deleteById(id);
    }

    public ProductEntity updateProductByID(Long id, ProductEntity entity) {
        ProductEntity oldProduct = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (entity.getProductName() != null && !entity.getProductName().isEmpty()) {
            oldProduct.setProductName(entity.getProductName());
        }
        if (entity.getDescription() != null) {
            oldProduct.setDescription(entity.getDescription());
        }
        if (entity.getStockQuantity() > 0) {
            oldProduct.setStockQuantity(entity.getStockQuantity());
        }

        return repository.save(oldProduct);
    }
}