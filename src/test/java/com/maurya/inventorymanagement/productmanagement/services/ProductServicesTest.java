package com.maurya.inventorymanagement.productmanagement.services;

import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.exception.NoLowStockProductException;
import com.maurya.inventorymanagement.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServicesTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServices service;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new ProductEntity();
        product.setProductId(1L);
        product.setProductName("Soap");
        product.setDescription("Test Product");
        product.setStockQuantity(10);
    }


    @Test
    void testIncreaseStockByID_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(ProductEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = service.increaseStockByID(1L, 5);

        assertEquals(15, product.getStockQuantity());
        assertTrue(result.contains("Updated product with id: 1"));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(product);
    }

    @Test
    void testIncreaseStockByID_ProductNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.increaseStockByID(1L, 5));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Product not found"));
    }


    @Test
    void testDecreaseStockByID_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(ProductEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = service.decreaseStockByID(1L, 5);

        assertEquals(5, product.getStockQuantity());
        assertTrue(result.contains("Updated product with id: 1"));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(product);
    }

    @Test
    void testDecreaseStockByID_InsufficientStock() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.decreaseStockByID(1L, 20));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Insufficient stock quantity"));
    }

    @Test
    void testDecreaseStockByID_ProductNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.decreaseStockByID(1L, 5));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Product not found"));
    }

    @Test
    void testLowThreshold_Success() {
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(2L);
        product2.setProductName("Dettol");
        product2.setStockQuantity(3);
        product2.setThresholdQuantity(5);

        when(repository.findAll()).thenReturn(Arrays.asList(product, product2));

        List<ProductEntity> lowStock = service.lowThreshold();

        assertEquals(1, lowStock.size());
        assertEquals("Dettol", lowStock.get(0).getProductName());
    }

    @Test
    void testLowThreshold_NoLowStockProducts() {
        ProductEntity product2 = new ProductEntity();
        product2.setProductId(2L);
        product2.setProductName("Dettol");
        product2.setStockQuantity(10);
        product2.setThresholdQuantity(5);

        when(repository.findAll()).thenReturn(Arrays.asList(product, product2));

        NoLowStockProductException ex = assertThrows(NoLowStockProductException.class,
                () -> service.lowThreshold());

        assertEquals("No products found below the threshold value", ex.getMessage());
    }

    @Test
    void testLowThreshold_EmptyRepository() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        NoLowStockProductException ex = assertThrows(NoLowStockProductException.class,
                () -> service.lowThreshold());

        assertEquals("No products found below the threshold value", ex.getMessage());
    }
}