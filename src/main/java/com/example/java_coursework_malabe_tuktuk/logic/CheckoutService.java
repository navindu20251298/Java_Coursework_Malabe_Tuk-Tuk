package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.CartItem;
import com.example.java_coursework_malabe_tuktuk.model.Part;
import com.example.java_coursework_malabe_tuktuk.model.Transaction;

import java.util.List;

public class CheckoutService {

    public void validateCart(List<CartItem> cart) {

        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Add items before checking out.");
        }

        for (CartItem item : cart) {
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero for " + item.getPart().getName());
            }
            if (item.getQuantity() > item.getPart().getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for " + item.getPart().getName()
                        + ". Available: " + item.getPart().getQuantity());
            }
        }
    }