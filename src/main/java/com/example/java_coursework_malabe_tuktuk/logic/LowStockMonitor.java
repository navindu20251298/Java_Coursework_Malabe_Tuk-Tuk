package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import java.util.ArrayList;
import java.util.List;

public class LowStockMonitor {

    public List<Part> findLowStockItems(List<Part> parts) {
        List<Part> lowStockItems = new ArrayList<>();

        for (Part p : parts) {
            if (p.getQuantity() < p.getThreshold()) {
                lowStockItems.add(p);
            }
        }

        return lowStockItems;
    }
}