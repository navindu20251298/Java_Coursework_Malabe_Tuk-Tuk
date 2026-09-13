package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Part;
import java.util.List;

public class PartSorter {

    public void sortByCategoryThenCode(List<Part> parts) {

        int n = parts.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {

                Part a = parts.get(j);
                Part b = parts.get(j + 1);

                boolean shouldSwap = false;

                int categoryCompare = a.getCategory().compareToIgnoreCase(b.getCategory());

                if (categoryCompare > 0) {
                    shouldSwap = true;
                } else if (categoryCompare == 0) {
                    if (a.getPartCode().compareToIgnoreCase(b.getPartCode()) > 0) {
                        shouldSwap = true;
                    }
                }

                if (shouldSwap) {
                    parts.set(j, b);
                    parts.set(j + 1, a);
                }
            }
        }
    }
}