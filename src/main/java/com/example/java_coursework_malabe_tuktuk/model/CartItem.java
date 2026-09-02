package com.example.java_coursework_malabe_tuktuk.model;

public class CartItem {
    private Part part;
    private int quantity;

    public CartItem(Part part, int quantity) {
        this.part = part;
        this.quantity = quantity;
    }

    public Part getPart() {
        return part;
    }
    public int getQuantity() {
        return quantity;
    }
}
