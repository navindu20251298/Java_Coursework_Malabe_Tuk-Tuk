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

    public double calculateSubtotal(List<CartItem> cart) {
        double subtotal = 0;
        for (CartItem item : cart) {
            subtotal += item.getLineTotal();
        }
        return subtotal;
    }

    public double calculateDiscount(List<CartItem> cart) {

        double totalDiscount = 0;
        boolean hasEngine = false;
        boolean hasElectrical = false;

        for (CartItem item : cart) {
            if (item.getQuantity() >= 3) {
                double itemDiscount = item.getLineTotal() * 0.05;
                totalDiscount += itemDiscount;
            }

            String category = item.getPart().getCategory();
            if (category.equalsIgnoreCase("engine")) {
                hasEngine = true;
            }
            if (category.equalsIgnoreCase("electrical")) {
                hasElectrical = true;
            }
        }

        if (hasEngine && hasElectrical) {
            double subtotalAfterBulk = calculateSubtotal(cart) - totalDiscount;
            double synergyDiscount = subtotalAfterBulk * 0.10;
            totalDiscount += synergyDiscount;
        }

        return totalDiscount;
    }

    public void deductStock(List<CartItem> cart) {
        for (CartItem item : cart) {
            Part part = item.getPart();
            part.setQuantity(part.getQuantity() - item.getQuantity());
        }
    }