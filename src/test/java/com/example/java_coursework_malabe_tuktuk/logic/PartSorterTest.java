package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartSorterTest {

    @Test
    public void testSortByCategoryThenCode() {

        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P003", "Item C", "BrandX", "engine", 100, 5, LocalDate.now(), 10, ""));
        parts.add(new Part("P001", "Item A", "BrandX", "brakes", 200, 5, LocalDate.now(), 10, ""));
        parts.add(new Part("P002", "Item B", "BrandX", "brakes", 150, 5, LocalDate.now(), 10, ""));

        PartSorter sorter = new PartSorter();
        sorter.sortByCategoryThenCode(parts);

        assertEquals("P001", parts.get(0).getPartCode());
        assertEquals("P002", parts.get(1).getPartCode());
        assertEquals("P003", parts.get(2).getPartCode());
    }

    @Test
    public void testSortWithSingleCategory() {

        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P005", "Item E", "BrandX", "electrical", 100, 5, LocalDate.now(), 10, ""));
        parts.add(new Part("P001", "Item A", "BrandX", "electrical", 100, 5, LocalDate.now(), 10, ""));
        parts.add(new Part("P003", "Item C", "BrandX", "electrical", 100, 5, LocalDate.now(), 10, ""));

        PartSorter sorter = new PartSorter();
        sorter.sortByCategoryThenCode(parts);

        assertEquals("P001", parts.get(0).getPartCode());
        assertEquals("P003", parts.get(1).getPartCode());
        assertEquals("P005", parts.get(2).getPartCode());
    }

    @Test
    public void testSortEmptyList() {
        List<Part> parts = new ArrayList<>();

        PartSorter sorter = new PartSorter();
        sorter.sortByCategoryThenCode(parts);

        assertEquals(0, parts.size());
    }
}