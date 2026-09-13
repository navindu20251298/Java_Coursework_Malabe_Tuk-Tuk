package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import java.util.List;

public class InventoryManager {

    public void addPart(List<Part> parts, Part newPart) throws IllegalArgumentException {

        if (newPart.getPartCode() == null || newPart.getPartCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Part code cannot be empty.");
        }
        if (newPart.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (newPart.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        for (Part p : parts) {
            if (p.getPartCode().equalsIgnoreCase(newPart.getPartCode())) {
                throw new IllegalArgumentException("A part with this code already exists.");
            }
        }

        parts.add(newPart);
    }