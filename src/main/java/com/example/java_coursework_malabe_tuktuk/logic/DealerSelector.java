package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Dealer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealerSelector {

    private Random random = new Random();

    public List<Dealer> selectRandomDealers(List<Dealer> allDealers, int count) {

        List<Dealer> selected = new ArrayList<>();

        if (allDealers.size() <= count) {
            selected.addAll(allDealers);
            sortByLocation(selected);
            return selected;
        }

        List<Integer> usedIndexes = new ArrayList<>();

        while (selected.size() < count) {
            int index = random.nextInt(allDealers.size());

            if (!usedIndexes.contains(index)) {
                usedIndexes.add(index);
                selected.add(allDealers.get(index));
            }
        }

        sortByLocation(selected);
        return selected;
    }

    private void sortByLocation(List<Dealer> dealers) {
        int n = dealers.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Dealer a = dealers.get(j);
                Dealer b = dealers.get(j + 1);

                if (a.getLocation().compareToIgnoreCase(b.getLocation()) > 0) {
                    dealers.set(j, b);
                    dealers.set(j + 1, a);
                }
            }
        }
    }
}
