package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import java.util.ArrayList;
import java.util.List;

public class SearchFilter {

    public List<Part> search(List<Part> parts, String keyword, String category, Double minPrice, Double maxPrice) {

        List<Part> results = new ArrayList<>();

        for (Part p : parts) {

            boolean matches = true;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = keyword.trim().toLowerCase();
                boolean nameMatches = p.getName().toLowerCase().contains(kw);
                boolean codeMatches = p.getPartCode().toLowerCase().contains(kw);
                if (!nameMatches && !codeMatches) {
                    matches = false;
                }
            }

            if (matches && category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All Categories")) {
                if (!p.getCategory().equalsIgnoreCase(category.trim())) {
                    matches = false;
                }
            }

            if (matches && minPrice != null) {
                if (p.getPrice() < minPrice) {
                    matches = false;
                }
            }

            if (matches && maxPrice != null) {
                if (p.getPrice() > maxPrice) {
                    matches = false;
                }
            }

            if (matches) {
                results.add(p);
            }
        }

        return results;
    }
}