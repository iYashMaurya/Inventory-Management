package com.maurya.inventorymanagement.productmanagement.services;

import com.maurya.inventorymanagement.productmanagement.dto.ProductResponseDTO;
import com.maurya.inventorymanagement.productmanagement.entity.ProductEntity;
import com.maurya.inventorymanagement.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServicesTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private InventoryServices service;

    private ProductEntity product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new ProductEntity();
        product.setProductId(1L);
        product.setProductName("Soap");
        product.setDescription("Test Product");
        product.setStockQuantity(10);
        product.setThresholdQuantity(5);
    }


    @Test
    void testCreateProduct_Success() {
        when(repository.save(product)).thenReturn(product);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO response = service.createProduct(product);

        assertNotNull(response);
        assertEquals(1L, response.getProductId());
        assertEquals("Soap", response.getProductName());
        assertEquals("Product created successfully", response.getMessage());

        verify(repository, times(1)).save(product);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct_SaveFailure() {
        when(repository.save(product)).thenReturn(product);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.createProduct(product));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Product not saved"));
    }

    @Test
    void testCreateProduct_ThresholdCanBeGreaterThanStock() {
        product.setStockQuantity(5);
        product.setThresholdQuantity(10);

        when(repository.save(product)).thenReturn(product);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO response = service.createProduct(product);

        assertEquals(10, response.getThresholdQuantity());
        assertEquals("Product created successfully", response.getMessage());
    }



    @Test
    void testGetProductByID_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductEntity found = service.getProductByID(1L);

        assertNotNull(found);
        assertEquals("Soap", found.getProductName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetProductByID_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.getProductByID(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Product not found"));
    }



    @Test
    void testDeleteProductByID_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deleteProductByID(1L));

        verify(repository, times(1)).deleteById(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProductByID_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.deleteProductByID(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Product not found"));
    }


    @Test
    void testUpdateProductByID_AllFieldsIncludingThreshold() {
        ProductEntity updated = new ProductEntity();
        updated.setProductName("Updated Soap");
        updated.setDescription("Updated Description");
        updated.setStockQuantity(25);
        updated.setThresholdQuantity(20);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(ProductEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        ProductEntity result = service.updateProductByID(1L, updated);

        assertEquals("Updated Soap", result.getProductName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(25, result.getStockQuantity());
        assertEquals(20, result.getThresholdQuantity());
    }

    @Test
    void testUpdateProductByID_PartialUpdate() {
        ProductEntity partial = new ProductEntity();
        partial.setProductName("New Name");
        partial.setThresholdQuantity(15);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(ProductEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        ProductEntity result = service.updateProductByID(1L, partial);

        assertEquals("New Name", result.getProductName());
        assertEquals("Test Product", result.getDescription());
        assertEquals(10, result.getStockQuantity());
        assertEquals(15, result.getThresholdQuantity());
    }

    @Test
    void testUpdateProductByID_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.updateProductByID(1L, product));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Product not found"));
    }
}