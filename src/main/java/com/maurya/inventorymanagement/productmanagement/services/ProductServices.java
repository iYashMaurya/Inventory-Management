package com.maurya.inventorymanagement.productmanagement.services;

import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServices {
    @Autowired
    private ProductRepository repository;

    @Transactional
    public String increaseStockByID (Long id, int quantity) {
       ProductEntity entity = repository.findById(id).orElse(null);
       if (entity != null) {
           entity.setStockQuantity(entity.getStockQuantity() + quantity);
           repository.save(entity);
       } else {
           return "Failed to find product with id: " + id;
       }

       return "Updated product with id: " + id + " with stock quantity: " + entity.getStockQuantity();

    }

    @Transactional
    public String decreaseStockByID (Long id, int quantity) {
        ProductEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            if (entity.getStockQuantity() < quantity) {
                return "Insufficient stock quantity for product with id: " + id;
            } else {
                entity.setStockQuantity(entity.getStockQuantity() - quantity);
                repository.save(entity);
                return "Updated product with id: " + id + " with stock quantity: " + entity.getStockQuantity();
            }
        }
        else {
            return "Failed to find product with id: " + id;
        }
    }
}
