package com.maurya.inventorymanagement.productmanagement.exception;

public class NoLowStockProductException extends RuntimeException {
    public NoLowStockProductException(String message) {
        super(message);
    }
}