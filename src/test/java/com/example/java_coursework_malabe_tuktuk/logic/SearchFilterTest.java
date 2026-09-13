package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchFilterTest {

    private List<Part> buildSampleParts() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part("P001", "Brake Pad", "TVS", "brakes", 1250.0, 7, LocalDate.now(), 10, ""));
        parts.add(new Part("P002", "Spark Plug", "NGK", "electrical", 850.0, 36, LocalDate.now(), 15, ""));
        parts.add(new Part("P003", "Piston Ring", "Bajaj", "engine", 20240.0, 25, LocalDate.now(), 20, ""));
        return parts;
    }

    @Test
    public void testFilterByKeyword() {
        SearchFilter filter = new SearchFilter();
        List<Part> results = filter.search(buildSampleParts(), "brake", null, null, null);

        assertEquals(1, results.size());
        assertEquals("P001", results.get(0).getPartCode());
    }

    @Test
    public void testFilterByCategory() {
        SearchFilter filter = new SearchFilter();
        List<Part> results = filter.search(buildSampleParts(), null, "engine", null, null);

        assertEquals(1, results.size());
        assertEquals("P003", results.get(0).getPartCode());
    }

    @Test
    public void testFilterByPriceRange() {
        SearchFilter filter = new SearchFilter();
        List<Part> results = filter.search(buildSampleParts(), null, null, 800.0, 2000.0);

        assertEquals(2, results.size()); // Brake Pad (1250) and Spark Plug (850)
    }

    @Test
    public void testCombinedFilters() {
        SearchFilter filter = new SearchFilter();
        List<Part> results = filter.search(buildSampleParts(), "spark", "electrical", 500.0, 1000.0);

        assertEquals(1, results.size());
        assertEquals("P002", results.get(0).getPartCode());
    }

    @Test
    public void testNoMatchesReturnsEmptyList() {
        SearchFilter filter = new SearchFilter();
        List<Part> results = filter.search(buildSampleParts(), "nonexistent", null, null, null);

        assertEquals(0, results.size());
    }
}