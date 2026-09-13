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
