package com.example.java_coursework_malabe_tuktuk.logic;

import com.example.java_coursework_malabe_tuktuk.model.Dealer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DealerSelectorTest {

    private List<Dealer> buildSampleDealers() {
        List<Dealer> dealers = new ArrayList<>();
        dealers.add(new Dealer("D001", "Malabe Auto Spares", "Malabe", "0771234567"));
        dealers.add(new Dealer("D002", "Kandy Tuk Parts", "Kandy", "0712345678"));
        dealers.add(new Dealer("D003", "Galle Wheels Depot", "Galle", "0783456789"));
        dealers.add(new Dealer("D004", "Negombo Motors", "Negombo", "0754567890"));
        dealers.add(new Dealer("D005", "Kurunegala Spares", "Kurunegala", "0765678901"));
        dealers.add(new Dealer("D006", "Jaffna Auto Parts", "Jaffna", "0776789012"));
        return dealers;
    }

    @Test
    public void testSelectsCorrectNumberOfDealers() {
        DealerSelector selector = new DealerSelector();
        List<Dealer> selected = selector.selectRandomDealers(buildSampleDealers(), 4);

        assertEquals(4, selected.size());
    }

    @Test
    public void testSelectedDealersAreUnique() {
        DealerSelector selector = new DealerSelector();
        List<Dealer> selected = selector.selectRandomDealers(buildSampleDealers(), 4);

        for (int i = 0; i < selected.size(); i++) {
            for (int j = i + 1; j < selected.size(); j++) {
                assertNotEquals(selected.get(i).getDealerId(), selected.get(j).getDealerId());
            }
        }
    }

    @Test
    public void testSelectedDealersAreSortedByLocation() {
        DealerSelector selector = new DealerSelector();
        List<Dealer> selected = selector.selectRandomDealers(buildSampleDealers(), 4);

        for (int i = 0; i < selected.size() - 1; i++) {
            String currentLocation = selected.get(i).getLocation();
            String nextLocation = selected.get(i + 1).getLocation();

            assertTrue(currentLocation.compareToIgnoreCase(nextLocation) <= 0);
        }
    }

    @Test
    public void testRequestingMoreDealersThanAvailableReturnsAll() {
        List<Dealer> smallList = new ArrayList<>();
        smallList.add(new Dealer("D001", "Malabe Auto Spares", "Malabe", "0771234567"));
        smallList.add(new Dealer("D002", "Kandy Tuk Parts", "Kandy", "0712345678"));

        DealerSelector selector = new DealerSelector();
        List<Dealer> selected = selector.selectRandomDealers(smallList, 4);

        assertEquals(2, selected.size());
    }

    @Test
    public void testMultipleRunsStillReturnUniqueSets() {
        DealerSelector selector = new DealerSelector();

        for (int i = 0; i < 10; i++) {
            List<Dealer> selected = selector.selectRandomDealers(buildSampleDealers(), 4);
            assertEquals(4, selected.size());

            for (int a = 0; a < selected.size(); a++) {
                for (int b = a + 1; b < selected.size(); b++) {
                    assertNotEquals(selected.get(a).getDealerId(), selected.get(b).getDealerId());
                }
            }
        }
    }
}