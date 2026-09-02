package com.example.java_coursework_malabe_tuktuk.model;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private List<CartItem> items;
    private double subtotal;
    private double discountApplied;
    private double total;
    private LocalDateTime timestamp;
}
