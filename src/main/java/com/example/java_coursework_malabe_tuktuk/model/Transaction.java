package com.example.java_coursework_malabe_tuktuk.model;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private List<CartItem> items;
    private double subtotal;
    private double discountApplied;
    private double total;
    private LocalDateTime timestamp;

    public Transaction(List<CartItem> items, double subtotal, double discountApplied, double total) {
        this.items = items;
        this.subtotal = subtotal;
        this.discountApplied = discountApplied;
        this.total = total;
        this.timestamp = LocalDateTime.now();
    }

    public List<CartItem> getItems() {
        return items;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public double getDiscountApplied() {
        return discountApplied;
    }
    public double getTotal() {
        return total;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
