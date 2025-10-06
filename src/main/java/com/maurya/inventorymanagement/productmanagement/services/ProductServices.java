package com.maurya.inventorymanagement.productmanagement.services;

import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.exception.NoLowStockProductException;
import com.maurya.inventorymanagement.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServices {

    @Autowired
    private ProductRepository repository;

    public String increaseStockByID(Long id, int quantity) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        entity.setStockQuantity(entity.getStockQuantity() + quantity);
        repository.save(entity);

        return "Updated product with id: " + id + " with stock quantity: " + entity.getStockQuantity();
    }

    public String decreaseStockByID(Long id, int quantity) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (entity.getStockQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock quantity");
        }

        entity.setStockQuantity(entity.getStockQuantity() - quantity);
        repository.save(entity);

        return "Updated product with id: " + id + " with stock quantity: " + entity.getStockQuantity();
    }

    public List<ProductEntity> lowThreshold() {
        List<ProductEntity> list = repository.findAll();
        List<ProductEntity> lowThreshold = new ArrayList<>();

        for (ProductEntity p : list) {
            if (p.getStockQuantity() <= p.getThresholdQuantity()) {
                lowThreshold.add(p);
            }
        }

        if (lowThreshold.isEmpty()) {
            throw new NoLowStockProductException("No products found below the threshold value");
        }

        return lowThreshold;
    }
}