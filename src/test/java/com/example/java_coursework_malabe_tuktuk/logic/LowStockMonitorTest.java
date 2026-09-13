package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LowStockMonitorTest {

    @Test
    public void testDetectsLowStockItems() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Item A", "Brand", "brakes", 100.0, 5, LocalDate.now(), 10, ""));
        parts.add(new Part("P002", "Item B", "Brand", "engine", 100.0, 25, LocalDate.now(), 20, ""));

        LowStockMonitor monitor = new LowStockMonitor();
        List<Part> lowStock = monitor.findLowStockItems(parts);

        assertEquals(1, lowStock.size());
        assertEquals("P001", lowStock.get(0).getPartCode());
    }

    @Test
    public void testNoLowStockItems() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Item A", "Brand", "brakes", 100.0, 50, LocalDate.now(), 10, ""));

        LowStockMonitor monitor = new LowStockMonitor();
        List<Part> lowStock = monitor.findLowStockItems(parts);

        assertEquals(0, lowStock.size());
    }

    @Test
    public void testEmptyListReturnsEmpty() {
        List<Part> parts = new ArrayList<>();

        LowStockMonitor monitor = new LowStockMonitor();
        List<Part> lowStock = monitor.findLowStockItems(parts);

        assertEquals(0, lowStock.size());
    }
}